package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_des_info")
public class productDesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "des_info_id")
    private int desInfoID;
    @ManyToOne
    @JoinColumn(name = "des_title_id", nullable = false)
    private productDesTitle desTitleId;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private product proID;
    @Column(name = "des_info", nullable = false)
    private String desInfo;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
}
