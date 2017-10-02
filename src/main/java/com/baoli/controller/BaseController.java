package com.baoli.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-10-02 22:00
 ************************************************************/

public class BaseController<T> {

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
