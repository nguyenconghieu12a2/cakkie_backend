package com.cakkie.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private int id;
    private String cateName;
    private Integer parentId;
    private int isDeleted;

    public CategoryDTO() {
    }

    public CategoryDTO(int id, String cateName, Integer parentId, int isDeleted) {
        this.id = id;
        this.cateName = cateName;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
    }
}
