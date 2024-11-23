package com.cakkie.backend.controller.adminProfile;

import com.cakkie.backend.model.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cakkie.backend.service.adminProfile.AdminProfileService;
import com.cakkie.backend.dto.adminProfile.AdminProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminProfileController {

    @Autowired
    private AdminProfileService adminProfileService;

    public AdminProfileController(AdminProfileService adminProfileService) {
        this.adminProfileService = adminProfileService;
    }

    @GetMapping("/admin-profile/{id}")
    public ResponseEntity<AdminProfileDTO> getAdminProfile(@PathVariable int id) {
        Optional<admin> opAdmin = adminProfileService.findById(id);

        if(opAdmin.isPresent()) {
            admin admin = opAdmin.get();

            AdminProfileDTO adminProfileDTO = new AdminProfileDTO(
                    admin.getId(),
                    admin.getFirstname(),
                    admin.getLastname(),
                    admin.getUsername(),
                    admin.getEmail(),
                    admin.getAvatarImg()
            );
            return ResponseEntity.ok(adminProfileDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-admin-profile/{id}")
    public ResponseEntity<AdminProfileDTO> updateAdminProfile(
            @PathVariable("id") int id,
            @RequestParam("firstName") String firstname,
            @RequestParam("lastName") String lastname,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam(value = "adminImage", required = false) MultipartFile image) {
        try {
            AdminProfileDTO updatedProfile = adminProfileService.updateAdminProfile(id, firstname, lastname, username, email, image);
            return ResponseEntity.ok(updatedProfile);
        }  catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(
            @PathVariable int id,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword){
        try{
            adminProfileService.changePassword(id, oldPassword, newPassword);
            return ResponseEntity.ok("Password changed successfully.");
        }catch(RuntimeException e){
            if(e.getMessage().equals("Incorrect old password")){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect old password");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
