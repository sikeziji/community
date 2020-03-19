package com.wangj.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    //问题详情
    private List<QuestionDTO> questions;
    //是否显示前一页
    private boolean showPrevious;
    //是否显示第一页
    private boolean showFirstPage;
    //是否显示下一页
    private boolean showNext;
    //是否显示最后一页
    private boolean showEndPage;
    //当前页码
    private Integer page;
    //页数
    private List<Integer> pages = new ArrayList<>();

    //最大页数
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page) {
       this.totalPage = totalCount;
        this.page = page;

        pages.add(page);
        for (int i = 1; i < 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }

        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }


        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
