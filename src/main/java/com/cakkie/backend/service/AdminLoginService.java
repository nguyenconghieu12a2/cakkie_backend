package com.cakkie.backend.service;
import com.cakkie.backend.api.AdminLoginAPI;
import com.cakkie.backend.model.admin;
import com.cakkie.backend.repository.AdminLoginRepo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AdminLoginService {
    private final JwtService jwtService;
    private AdminLoginRepo adminLoginRepo;

    public AdminLoginService(JwtService jwtService, AdminLoginRepo adminLoginRepo) {
        this.jwtService = jwtService;
        this.adminLoginRepo = adminLoginRepo;
    }

    public String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String loginAdmin(AdminLoginAPI adminLogin) {
        Optional<admin> opAdmin = adminLoginRepo.findByUsernameIgnoreCase(adminLogin.getUsername());
        if(opAdmin.isPresent()) {
            admin admin = opAdmin.get();
            if(admin.getPassword().equals(getMd5(adminLogin.getPassword()))) {
                return jwtService.generateJwt(admin);
            }
        }
        return null;
    }

    public admin saveAdmin(admin user) {
        return adminLoginRepo.save(user);
    }
}
