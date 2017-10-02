package com.baoli.service.impl;

import com.baoli.mapper.UploadFileInfoMapper;
import com.baoli.model.UploadFileInfo;
import com.baoli.service.IUploadFileInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 9:00
 ************************************************************/

@Service
public class UploadFileInfoServiceImpl implements IUploadFileInfoService {

    @Autowired
    private UploadFileInfoMapper mapper;

    @Override
    public UploadFileInfo insert(UploadFileInfo uploadFileInfo) {
        uploadFileInfo.setId(mapper.insert(uploadFileInfo));
        return uploadFileInfo;
    }

    @Override
    public PageInfo<UploadFileInfo> getUploadFileList(String fileName, String createUser, Double fileSizeStart, Double fileSizeEnd, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> params = new HashedMap();
        params.put("fileName", fileName);
        params.put("createUser", createUser);
        params.put("fileSizeStart", fileSizeStart);
        params.put("fileSizeEnd", fileSizeEnd);
        List<UploadFileInfo> fileInfoLists = mapper.getUploadFileList(params);

        return new PageInfo<>(fileInfoLists);
    }

    @Override
    public void deleteFile(Integer id) {
        mapper.delete(id);
    }
}
