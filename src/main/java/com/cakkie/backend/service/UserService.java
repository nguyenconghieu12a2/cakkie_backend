package com.cakkie.backend.service;

import com.cakkie.backend.dto.EditBody;
import com.cakkie.backend.dto.LoginBody;
import com.cakkie.backend.dto.RegistrationBody;
import com.cakkie.backend.exception.UserAlreadyExistException;
import com.cakkie.backend.model.shoppingCart;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.model.userStatus;
import com.cakkie.backend.repository.ShoppingCartRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final JwtService jwtService;
    private UserSiteRepository userSiteRepository;
    private UserStatusRepository userStatusRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private final JavaMailSender emailSender;

    private final Map<String, String> otpCache = new ConcurrentHashMap<>();

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    public UserService(UserSiteRepository userSiteRepository, JwtService jwtService, UserStatusRepository userStatusRepository, ShoppingCartRepository shoppingCartRepository, JavaMailSender emailSender) {
        this.userSiteRepository = userSiteRepository;
        this.jwtService = jwtService;
        this.userStatusRepository = userStatusRepository;
        this.shoppingCartRepository = shoppingCartRepository;
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
        // Check if the user already exists by username or email
        if (userSiteRepository.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                || userSiteRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        // Fetch the "Active" status (id = 1)
        userStatus activeStatus = userStatusRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Active user status not found"));

        // Create a new user
        userSite userSite = new userSite();
        userSite.setFirstname(registrationBody.getFirstname());
        userSite.setLastname(registrationBody.getLastname());
        userSite.setUsername(registrationBody.getUsername());
        userSite.setGender(registrationBody.getGender());
        if ("male".equalsIgnoreCase(registrationBody.getGender())) {
            userSite.setImage("male.jpg");
        } else if ("female".equalsIgnoreCase(registrationBody.getGender())) {
            userSite.setImage("female.jpg");
        }

        // Convert LocalDate (from RegistrationBody) to java.util.Date for userSite
        LocalDate birthdayLocalDate = registrationBody.getBirthday();
        Date birthdayDate = Date.from(birthdayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        userSite.setBirthday(birthdayDate);

        userSite.setEmail(registrationBody.getEmail());
        userSite.setPhone(registrationBody.getPhone());
        userSite.setPassword(getMd5(registrationBody.getPassword()));

        // Set the account creation date
        userSite.setAccountCreateDate(new Date()); // Current date-time in java.util.Date

        // Set the "Active" status
        userSite.setStatusId(activeStatus);

        // Save user to the database
        userSite savedUser = userSiteRepository.save(userSite);

        // Create a new shopping cart for the user
        shoppingCart cart = new shoppingCart();
        cart.setUserId(savedUser);
        shoppingCartRepository.save(cart);

        return savedUser;
    }

    public String loginUser(LoginBody loginBody) {
        Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(loginBody.getEmail());
        if (opUser.isPresent()) {
            userSite user = opUser.get();

            // Check if the password matches
            if (!user.getPassword().equals(getMd5(loginBody.getPassword()))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
            }

            // Check if user status is banned (status == 2)
            if (user.getStatusId().getId() == 2) {
                String reason = user.getBannedReason() != null ? user.getBannedReason() : "No reason provided.";
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account has been banned from this shop! Reason: " + reason);
            }

            // Check if user status is removed (status == 3)
            if (user.getStatusId().getId() == 3) {
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
                // Convert LocalDate to java.util.Date before setting (if userSite uses Date)
                user.setBirthday(java.sql.Date.valueOf(updatedInfo.getBirthday()));
            }
            if (updatedInfo.getPhone() != null) {
                user.setPhone(updatedInfo.getPhone());
            }

            // Save the updated user and return the saved entity
            return userSiteRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with email " + updatedInfo.getEmail() + " not found.");
        }
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        // Find the user by email
        Optional<userSite> opUser = userSiteRepository.findByEmailIgnoreCase(email);

        if (opUser.isPresent()) {
            userSite user = opUser.get();

            // Verify the current password
            if (!user.getPassword().equals(getMd5(currentPassword))) {
                return false; // Current password is incorrect
            }

            // Check if the new password is the same as the current password
            if (user.getPassword().equals(getMd5(newPassword))) {
                throw new IllegalArgumentException("New password cannot be the same as the current password");
            }

            // Update the password with the new hashed password
            user.setPassword(getMd5(newPassword));
            userSiteRepository.save(user);
            return true; // Password change successful
        }

        return false; // User not found or other failure
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

    public int getUserIdByEmail(String email) {
        Optional<userSite> user = userSiteRepository.findByEmailIgnoreCase(email);
        return user != null ? user.get().getId() : null;
    }


}
