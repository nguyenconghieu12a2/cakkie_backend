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
@Table(name = "order_status")
public class orderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    @OneToMany(mappedBy = "orderStatusId")
    private List<shopOrder> shopOrdersList;
}
