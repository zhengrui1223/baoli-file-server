package com.baoli.vo;

import com.baoli.vo.base.BaseVO;

import java.util.List;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-26 12:48
 ************************************************************/
public class PaginationList<T extends BaseVO> {
    private List<T> elements;
    private Integer pageSize;
    private Integer pageNumber;

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
