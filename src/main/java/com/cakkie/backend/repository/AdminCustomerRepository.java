//package com.cakkie.backend.repository;
//
//import com.cakkie.backend.model.userSite;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface AdminCustomerRepository extends JpaRepository<userSite, Integer> {
//    @Query(value = "UPDATE userSite SET statusId = 2 , bannedReason = ?2 WHERE id = ?1")
//    void bannedCustomerById(int id, String bannedReason);
//}
