package com.baoli.controller;

import com.baoli.model.UploadFileInfo;
import com.baoli.service.IUploadFileInfoService;
import com.baoli.util.BaoLiBeanUtil;
import com.baoli.util.FastDFSClientWrapper;
import com.baoli.util.ImageCheck;
import com.baoli.vo.UploadFileInfoVO;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    private FastDFSClientWrapper dfsClient;
    @Autowired
    private IUploadFileInfoService uploadFileInfoService;
    @Autowired
    private ImageCheck imageCheck;

    @RequestMapping("/uploadPage")
    public String toUploadPage() {
        return "upload_file/upload_page";
    }

    @RequestMapping("/uploadFileList")
    public String getUploadFileList(Model model,
                                    @RequestParam(required = false) String fileName, @RequestParam(required = false) String createUser,
                                    @RequestParam(required = false) Double fileSizeStart, @RequestParam(required = false) Double fileSizeEnd,
                                    @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<UploadFileInfo> pageInfo = uploadFileInfoService.getUploadFileList(fileName, createUser, fileSizeStart, fileSizeEnd, pageNum, pageSize);
        model.addAttribute("uploadFileList", BaoLiBeanUtil.convertUploadFileInfos2UploadFileInfoVOs(pageInfo.getList()));
        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());
        //获得总条数
        model.addAttribute("totalElements", pageInfo.getTotal());

        //回显搜索项
        model.addAttribute("fileName", fileName);
        model.addAttribute("createUser", createUser);
        model.addAttribute("fileSizeStart", fileSizeStart);
        model.addAttribute("fileSizeEnd", fileSizeEnd);

        return "upload_file/upload_file_List";
    }

    @ResponseBody
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public Map<String, List<String>> uploadFiles(@RequestParam("files") CommonsMultipartFile[] files) {
        Map<String, List<String>> resultMap = new HashMap<>();
        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(files)) {
            for (MultipartFile fileItem : files) {
                try {
                    if (fileItem.getSize() > 0) {
                        StorePath storePath = dfsClient.uploadFile(fileItem);
                        UploadFileInfoVO uploadFileInfoVO = new UploadFileInfoVO();
                        uploadFileInfoVO.setFileName(fileItem.getOriginalFilename());
                        uploadFileInfoVO.setFilePath(dfsClient.getResAccessUrl(storePath));
                        uploadFileInfoVO.setGroupName(storePath.getGroup());
                        uploadFileInfoVO.setStorePath(storePath.getPath());
                        uploadFileInfoVO.setImageType(imageCheck.isImage(fileItem.getOriginalFilename()));
                        uploadFileInfoVO.setFileSize(getFileSizeAsMB(fileItem.getSize()));
                        uploadFileInfoVO.setStorePath(storePath.getPath());
                        uploadFileInfoVO.setCreateUser("Admin");
                        uploadFileInfoVO.setFileExtension(getExtName(fileItem.getOriginalFilename(), '.'));
                        uploadFileInfoService.insert(BaoLiBeanUtil.convertUploadFileInfoVO2UploadFileInfo(uploadFileInfoVO));
                        successList.add(fileItem.getOriginalFilename());
                    }
                } catch (IOException e) {
                    failList.add(fileItem.getOriginalFilename());
                    logger.error(fileItem.getOriginalFilename() + " upload failed, cause by: " + e.getMessage());
                    continue;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(successList)) {
            resultMap.put("successList", successList);
        }
        if (CollectionUtils.isNotEmpty(failList)) {
            resultMap.put("failList", failList);
        }
        return resultMap;
    }

    @RequestMapping("/deleteFile")
    public String deleteFile(@RequestParam Integer id, @RequestParam String filePath) {
        dfsClient.deleteFile(filePath);
        uploadFileInfoService.deleteFile(id);
        return "redirect:/uploadFileList";
    }

    @RequestMapping("/deleteFiles")
    public String deleteFiles(@RequestParam Integer[] ids, @RequestParam String[] filePaths) {
        if (ArrayUtils.isNotEmpty(ids) && ArrayUtils.isNotEmpty(filePaths)) {
            for (int i=0; i< ids.length; i++) {
                dfsClient.deleteFile(filePaths[i]);
                uploadFileInfoService.deleteFile(ids[i]);
            }
        }
        return "redirect:/uploadFileList";
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam String groupName, @RequestParam String storePath, @RequestParam String fileName,
                             HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置响应头为下载
            response.setContentType(request.getServletContext().getMimeType(fileName));
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            ServletOutputStream outputStream = response.getOutputStream();
            dfsClient.downloadFile(groupName, storePath, outputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Double getFileSizeAsMB(Long fileSize) {
        BigDecimal source = new BigDecimal(fileSize);
        BigDecimal divide = source.divide(new BigDecimal(1024 * 1024), 5, RoundingMode.HALF_UP);
        return divide.doubleValue();
    }

    private String getExtName(String s, char split) {
        int i = s.indexOf(split);
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? " " : s.substring(i, s.length()) : " ");
    }

}
