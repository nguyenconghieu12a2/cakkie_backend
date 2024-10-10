package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banners")
public class banners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "image", nullable = true, columnDefinition = "TEXT")
    private String image;
    @Column(name = "link", nullable = true,  columnDefinition = "NVARCHAR(MAX)")
    private String link;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
