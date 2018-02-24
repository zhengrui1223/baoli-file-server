package com.baoli.service.impl;

import com.baoli.exception.BLServiceException;
import com.baoli.mapper.UploadFileInfoMapper;
import com.baoli.model.UploadFileInfo;
import com.baoli.service.IUploadFileInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UploadFileInfoServiceImpl.class);

    @Autowired
    private UploadFileInfoMapper mapper;

    @Override
    public UploadFileInfo insert(UploadFileInfo uploadFileInfo) throws BLServiceException {
        uploadFileInfo.setId(mapper.insert(uploadFileInfo));
        return uploadFileInfo;
    }

    @Override
    public PageInfo<UploadFileInfo> getUploadFileList(String fileName, String createUser,
                                                      Double fileSizeStart, Double fileSizeEnd,
                                                      Integer pageNum, Integer pageSize) throws BLServiceException {
        logger.info(String.format("Retrieve UploadFileInfo by [fileName = %s, createUser = %s, fileSizeStart = %s, fileSizeEnd = %s," +
                " pageNum = %s, pageSize = %s]", fileName, createUser, fileSizeStart, fileSizeEnd, pageNum, pageSize));
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> params = new HashedMap();
        params.put("fileName", fileName);
        params.put("createUser", createUser);
        params.put("fileSizeStart", fileSizeStart);
        params.put("fileSizeEnd", fileSizeEnd);
        List<UploadFileInfo> fileInfoLists = mapper.getUploadFileList(params);
        try {
            logger.info(String.format("UploadFileInfo list is: %s", new ObjectMapper().writeValueAsString(fileInfoLists)));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return new PageInfo<>(fileInfoLists);
    }

    @Override
    public void deleteFile(Integer id) throws BLServiceException {
        mapper.delete(id);
    }
}
