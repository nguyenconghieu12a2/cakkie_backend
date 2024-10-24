package com.cakkie.backend.service;

import com.cakkie.backend.api.model.LoginBody;
import com.cakkie.backend.api.model.RegistrationBody;
import com.cakkie.backend.exception.UserAlreadyExistException;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.model.userStatus;
import com.cakkie.backend.repository.UserSiteRepository;
import com.cakkie.backend.repository.UserStatusRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final JwtService jwtService;
    private UserSiteRepository userSiteRepository;
    private UserStatusRepository userStatusRepository;

    public UserService(UserSiteRepository userSiteRepository, JwtService jwtService, UserStatusRepository userStatusRepository) {
        this.userSiteRepository = userSiteRepository;
        this.jwtService = jwtService;
        this.userStatusRepository = userStatusRepository;
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

    public userSite registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException {

        if (userSiteRepository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                || userSiteRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        // Fetch the "Active" status (id = 1)
        userStatus activeStatus = userStatusRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Active user status not found"));

        userSite userSite = new userSite();
        userSite.setFirstname(registrationBody.getFirstname());
        userSite.setLastname(registrationBody.getLastname());
        userSite.setUsername(registrationBody.getUsername());
        userSite.setGender(registrationBody.getGender());
        if(registrationBody.getGender().equals("male")) {
            userSite.setImage("/images/male.jpg");
        } else if (registrationBody.getGender().equals("female")) {
            userSite.setImage("/images/female.jpg");
        }
        userSite.setBirthday(registrationBody.getBirthday());
        userSite.setEmail(registrationBody.getEmail());
        userSite.setPhone(registrationBody.getPhone());
        userSite.setPassword(getMd5(registrationBody.getPassword()));
        userSite.setAccountCreateDate(new Date(new java.util.Date().getTime()));

        // Set the "Active" status
        userSite.setStatusId(activeStatus);

        return userSiteRepository.save(userSite);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(loginBody.getEmail());
        if(opUser.isPresent()) {
            userSite user = opUser.get();
            if(user.getPassword().equals(getMd5(loginBody.getPassword()))) {
                return jwtService.generateJwt(user);
            }
        }
        return null;
    }

    public userSite saveUser(userSite user) {
        return userSiteRepository.save(user);
    }
}
