package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class productDesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int desInfoID;
    @ManyToOne
    @JoinColumn(name = "desTitleID", nullable = false)
    private productDesTitle desTitleID;
    @ManyToOne
    @JoinColumn(name = "proID", nullable = false)
    private product proID;
    @Column(name = "desInfo", nullable = false)
    private String desInfo;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
