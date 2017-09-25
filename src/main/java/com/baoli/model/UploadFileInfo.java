package com.baoli.model;

import com.baoli.model.base.BaseEntity;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 8:38
 ************************************************************/
public class UploadFileInfo extends BaseEntity {

    private String fileName;
    private String filePath;
    private String groupName;
    private String storePath;
    private Boolean imageType;

    public Boolean getImageType() {
        return imageType;
    }

    public void setImageType(Boolean imageType) {
        this.imageType = imageType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
