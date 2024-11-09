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
@Table(name = "productDesTitle")
public class productDesTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int desTitleID;
    @Column(name = "desTitleName", nullable = false)
    private String desTitlename;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    @OneToMany(mappedBy = "desTitleId")
    private List<productDesInfo> productDesInfoList;
}
