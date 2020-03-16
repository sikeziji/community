package com.wangj.community.dto;

import com.wangj.community.module.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCoount;
    private Integer likeCount;
    private User user;
}
