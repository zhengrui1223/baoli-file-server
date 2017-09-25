package com.baoli.service;

import com.baoli.model.UploadFileInfo;

import java.util.List;

/**
 * Created by MOVITECH-5-e450 on 2017/9/22.
 */
public interface IUploadFileInfoService {
    UploadFileInfo insert(UploadFileInfo uploadFileInfo);

    List<UploadFileInfo> getUploadFileList();

    void deleteFile(Integer id);
}
