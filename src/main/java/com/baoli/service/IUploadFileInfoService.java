package com.baoli.service;

import com.baoli.model.UploadFileInfo;
import com.github.pagehelper.PageInfo;

/**
 * Created by MOVITECH-5-e450 on 2017/9/22.
 */
public interface IUploadFileInfoService {
    UploadFileInfo insert(UploadFileInfo uploadFileInfo);

    PageInfo<UploadFileInfo> getUploadFileList(String fileName, String createUser, Double fileSizeStart, Double fileSizeEnd, Integer pageNum, Integer pageSize);

    void deleteFile(Integer id);
}
