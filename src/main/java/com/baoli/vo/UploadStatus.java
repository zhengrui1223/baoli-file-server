package com.baoli.vo;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-20 18:07
 ************************************************************/
public class UploadStatus {

    private List<String> successList = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    public List<String> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
