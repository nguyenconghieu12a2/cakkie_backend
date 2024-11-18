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
    private int subId;
    private String subName;
    private int subSubId;
    private String subSubName;

    public CategoryDTO() {
    }

    public CategoryDTO(int id, String cateName, Integer parentId, int isDeleted) {
        this.id = id;
        this.cateName = cateName;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
    }

    public CategoryDTO(int id, String cateName, int subId, String subName, int subSubId, String subSubName, int isDeleted) {
        this.id = id;
        this.cateName = cateName;
        this.subId = subId;
        this.subName = subName;
        this.subSubId = subSubId;
        this.subSubName = subSubName;
        this.isDeleted = isDeleted;
    }

    public CategoryDTO(int subId, String subName, int subSubId, String subSubName, int isDeleted) {
        this.subId = subId;
        this.subName = subName;
        this.subSubId = subSubId;
        this.subSubName = subSubName;
        this.isDeleted = isDeleted;
    }
}
