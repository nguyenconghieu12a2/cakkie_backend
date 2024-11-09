package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cate_name", nullable = false, length = 255)
    private String cateName;
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private category parentId;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    @OneToMany(mappedBy = "categoryId")
    private List<discountCategory> discountCategoryList;

    @OneToMany(mappedBy = "parentId")
    private List<category> categoryList;

    @OneToMany(mappedBy = "categoryID")
    private List<product> productList;
}
