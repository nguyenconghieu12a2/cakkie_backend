package com.cakkie.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString // Adding custom toString handling
@Table(name = "user_status")
public class userStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnore
    @ToString.Exclude  // Prevent recursion in toString method
    @OneToMany(mappedBy = "statusId") // Prefer LAZY fetching
    private List<userSite> userList;  // Users associated with this status
}
