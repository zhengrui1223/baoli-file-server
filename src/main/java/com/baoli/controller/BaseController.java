package com.baoli.controller;

import com.baoli.exception.BLServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-10-02 22:00
 ************************************************************/

public class BaseController<T> {

    @Autowired
    protected ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 捕获controller中抛出的异常， 理论上所有的异常都应该抛到controller层，再由BaseController中统一处理
     * {"status":"error","errorCode":"testException","errorMsg":"error test"}
     * @param si
     * @return
     * @throws IOException
     */
    @ExceptionHandler(BLServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(BLServiceException si) throws IOException {
        logger.error("handle exception:", si);
        Map<String, String> json = new HashMap<>();
        json.put("status", "error");
        json.put("errorCode", si.getErrorCode());
        json.put("errorMsg", si.getErrorMsg());
        return objectMapper.writeValueAsString(json);
    }

    protected void setPageInfo2Model(Model model, PageInfo<T> pageInfo) {
        //获得当前页
        model.addAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addAttribute("isLastPage", pageInfo.isIsLastPage());
        //获得总条数
        model.addAttribute("totalElements", pageInfo.getTotal());
    }

}
