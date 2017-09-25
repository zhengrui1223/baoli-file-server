package com.baoli.service.impl;

import com.baoli.mapper.UploadFileInfoMapper;
import com.baoli.model.UploadFileInfo;
import com.baoli.service.IUploadFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<UploadFileInfo> getUploadFileList() {
        return mapper.getAll();
    }

    @Override
    public void deleteFile(Integer id) {
        mapper.delete(id);
    }
}
