package com.cakkie.backend.service;

import com.cakkie.backend.dto.EditBody;
import com.cakkie.backend.dto.LoginBody;
import com.cakkie.backend.dto.RegistrationBody;
import com.cakkie.backend.exception.UserAlreadyExistException;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.model.userStatus;
import com.cakkie.backend.repository.UserSiteRepository;
import com.cakkie.backend.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final JwtService jwtService;
    private UserSiteRepository userSiteRepository;
    private UserStatusRepository userStatusRepository;
    private final JavaMailSender emailSender;

    private final Map<String, String> otpCache = new ConcurrentHashMap<>();

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    public UserService(UserSiteRepository userSiteRepository, JwtService jwtService, UserStatusRepository userStatusRepository, JavaMailSender emailSender) {
        this.userSiteRepository = userSiteRepository;
        this.jwtService = jwtService;
        this.userStatusRepository = userStatusRepository;
        this.emailSender = emailSender;
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
            userSite.setImage("male.jpg");
        } else if (registrationBody.getGender().equals("female")) {
            userSite.setImage("female.jpg");
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
        if (opUser.isPresent()) {
            userSite user = opUser.get();

            // Check if the password matches
            if (!user.getPassword().equals(getMd5(loginBody.getPassword()))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
            }

            // Check if user status is active (status == 1)
            if (user.getStatusId().getId() == 2) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account has been banned from this shop!");
            }

            if (user.getStatusId().getId() == 2) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account has been removed from this shop!");
            }

            // If all checks pass, generate and return JWT
            return jwtService.generateJwt(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found.");
        }
    }

    public userSite saveUser(userSite user) {
        return userSiteRepository.save(user);
    }

    public userSite updateUser(EditBody updatedInfo) {
        Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(updatedInfo.getEmail());
        if (opUser.isPresent()) {
            userSite user = opUser.get();

            // Update fields only if they are provided in updatedInfo
            if (updatedInfo.getFirstname() != null) {
                user.setFirstname(updatedInfo.getFirstname());
            }
            if (updatedInfo.getLastname() != null) {
                user.setLastname(updatedInfo.getLastname());
            }
            if (updatedInfo.getUsername() != null) {
                // Check if username is different before setting
                if (!user.getUsername().equalsIgnoreCase(updatedInfo.getUsername())) {
                    user.setUsername(updatedInfo.getUsername());
                }
            }
            if (updatedInfo.getGender() != null) {
                user.setGender(updatedInfo.getGender());
                // Set the default image based on gender
                if (updatedInfo.getGender().equalsIgnoreCase("male")) {
                    user.setImage("male.jpg");
                } else if (updatedInfo.getGender().equalsIgnoreCase("female")) {
                    user.setImage("female.jpg");
                }
            }
            if (updatedInfo.getBirthday() != null) {
                user.setBirthday(updatedInfo.getBirthday());
            }

            if(updatedInfo.getPhone() != null) {
                user.setPhone(updatedInfo.getPhone());
            }

            // Save the updated user and return the saved entity
            return userSiteRepository.save(user);
        }

        // Return null or handle the case where the user was not found
        return null;
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        // Find the user by email
        Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(email);

        if (opUser.isPresent()) {
            userSite user = opUser.get();

            // Verify the current password
            if (user.getPassword().equals(getMd5(currentPassword))) {
                // Update the password with the new hashed password
                user.setPassword(getMd5(newPassword));
                userSiteRepository.save(user);
                return true; // Password change successful
            }
        }

        return false; // Password change unsuccessful
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    public void sendPasswordResetEmail(String email) {
        Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            // Generate OTP
            String otp = generateOtp();

            // Store OTP in cache
            otpCache.put(email, otp);

            // Send OTP email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset OTP");
            message.setText("Your OTP for password reset is: " + otp);

            emailSender.send(message);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found.");
        }
    }

    public String verifyOtpAndGenerateResetLink(String email, String otp) {
        // Check if OTP matches the one in the cache
        if (otp.equals(otpCache.get(email))) {
            // Generate a unique token for the reset link
            String resetToken = UUID.randomUUID().toString();

            // Store this token in the cache temporarily (or in a database)
            otpCache.put(email + "_resetToken", resetToken);

            // Return the reset link with the email and token as query parameters
            return frontendBaseUrl + "/reset-password?email=" + email + "&token=" + resetToken;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP.");
        }
    }

    public boolean validateResetToken(String email, String token) {
        // Validate that the reset token matches the one stored in the cache
        String cachedToken = otpCache.get(email + "_resetToken");
        return token.equals(cachedToken);
    }

    public void updatePassword(String email, String newPassword) {
        Optional<userSite> userOptional = userSiteRepository.findByEmailIgnoreCase(email);
        if (userOptional.isPresent()) {
            userSite user = userOptional.get();
            user.setPassword(getMd5(newPassword)); // Hash the new password before saving
            userSiteRepository.save(user);

            // Optionally, clear the reset token and OTP from cache
            otpCache.remove(email);
            otpCache.remove(email + "_resetToken");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
    }
}
