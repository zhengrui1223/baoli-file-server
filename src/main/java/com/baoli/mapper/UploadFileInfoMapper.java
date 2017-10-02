package com.baoli.mapper;

import com.baoli.mapper.base.BaseMapper;
import com.baoli.model.UploadFileInfo;

import java.util.List;
import java.util.Map;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 8:38
 ************************************************************/

public interface UploadFileInfoMapper extends BaseMapper<UploadFileInfo, Integer> {
    List<UploadFileInfo> getUploadFileList(Map<String, Object> params);
}
