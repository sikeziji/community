package com.wangj.community.dto;

import lombok.Data;

@Data
public class ComentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
