package com.cakkie.backend.dto.adminCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCategoryDTO {
    private int id;
    private String cateName;
    private Integer parentId;
    private int isDeleted;
    private int subId;
    private String subName;
    private int subSubId;
    private String subSubName;

    public AdminCategoryDTO() {
    }

    public AdminCategoryDTO(int id, String cateName, Integer parentId, int isDeleted) {
        this.id = id;
        this.cateName = cateName;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
    }

    public AdminCategoryDTO(int id, String cateName, int subId, String subName, int subSubId, String subSubName, int isDeleted) {
        this.id = id;
        this.cateName = cateName;
        this.subId = subId;
        this.subName = subName;
        this.subSubId = subSubId;
        this.subSubName = subSubName;
        this.isDeleted = isDeleted;
    }

    public AdminCategoryDTO(int subId, String subName, int subSubId, String subSubName, int isDeleted) {
        this.subId = subId;
        this.subName = subName;
        this.subSubId = subSubId;
        this.subSubName = subSubName;
        this.isDeleted = isDeleted;
    }
}
