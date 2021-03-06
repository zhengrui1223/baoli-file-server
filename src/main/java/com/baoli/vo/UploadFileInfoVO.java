package com.baoli.vo;

import com.baoli.vo.base.BaseVO;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 8:57
 ************************************************************/
public class UploadFileInfoVO extends BaseVO{

    private String fileName;
    private String filePath;
    private String groupName;
    private String storePath;
    private Boolean imageType;
    private Double fileSize;
    private String fileExtension;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

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
