package com.cakkie.backend.repository;

import com.cakkie.backend.model.userSite;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserSiteRepository extends CrudRepository<userSite, Integer> {

    Optional<userSite> findByUsernameIgnoreCase(String username);

    Optional<userSite> findByEmailIgnoreCase(String email);

}
