package com.baoli.controller;

import com.baoli.model.UploadFileInfo;
import com.baoli.service.IUploadFileInfoService;
import com.baoli.util.BaoLiBeanUtil;
import com.baoli.util.FastDFSClientWrapper;
import com.baoli.util.ImageCheck;
import com.baoli.vo.UploadFileInfoVO;
import com.github.tobato.fastdfs.domain.StorePath;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

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
    public String getUploadFileList(ModelMap modelMap) {
        List<UploadFileInfo> uploadFileList = uploadFileInfoService.getUploadFileList();
        modelMap.addAttribute("uploadFileList", BaoLiBeanUtil.convertUploadFileInfos2UploadFileInfoVOs(uploadFileList));
        return "upload_file/upload_File_List";
    }

    @RequestMapping("/upload")
    public String upload(MultipartRequest multipartRequest) {
        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            List<MultipartFile> fileList = multipartRequest.getFiles(fileName);
            if (CollectionUtils.isNotEmpty(fileList)) {
                for (MultipartFile fileItem : fileList) {
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
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        continue;
                    }
                }
            }
        }
        return "redirect:/uploadFileList";
    }

    @RequestMapping("/deleteFile")
    public String deleteFile(@RequestParam Integer id, @RequestParam String filePath) {
        dfsClient.deleteFile(filePath);
        uploadFileInfoService.deleteFile(id);
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
        BigDecimal divide = source.divide(new BigDecimal(1024*1024), 5, RoundingMode.HALF_UP);
        return divide.doubleValue();
    }

    private String getExtName(String s, char split) {
        int i = s.indexOf(split);
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? " " : s.substring(i, s.length()) : " ");
    }

}
