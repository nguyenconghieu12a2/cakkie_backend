package com.cakkie.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productDesTitle")
public class productsDescription {
    @Id
    private int desId;
    private String DescriptionTitle;
    private String Description;


}
