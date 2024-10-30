package com.cakkie.backend.controller;

import com.cakkie.backend.dto.MailBody;
import com.cakkie.backend.exception.AdminNotFoundException;
import com.cakkie.backend.model.ForgotPassword;
import com.cakkie.backend.model.admin;
import com.cakkie.backend.repository.AdminProfileRepo;
import com.cakkie.backend.repository.ForgotPasswordRepo;
import com.cakkie.backend.service.AdminProfileService;
import com.cakkie.backend.service.EmailService;
import com.cakkie.backend.utils.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/forgotPassword")
public class ForgotPasswordController {

    private final AdminProfileRepo adminProfileRepo;

    private final AdminProfileService adminProfileService;

    private final EmailService emailService;

    private final ForgotPasswordRepo forgotPasswordRepo;

    public ForgotPasswordController(AdminProfileRepo adminProfileRepo, AdminProfileService adminProfileService, EmailService emailService, ForgotPasswordRepo forgotPasswordRepo) {
        this.adminProfileRepo = adminProfileRepo;
        this.adminProfileService = adminProfileService;
        this.emailService = emailService;
        this.forgotPasswordRepo = forgotPasswordRepo;
    }

    //send mail for email verification
    @PostMapping("/verifyEmail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        admin admin = adminProfileRepo.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException("Please provide an valid email!"));

        int otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot Password request: " + otp)
                .subject("OTP for Forgot password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .admin(admin)
                .build();

        emailService.sendSimpleMessage(mailBody);

        forgotPasswordRepo.save(fp);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        admin admin = adminProfileRepo.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException("Please provide an valid email!"));
        ForgotPassword fp = forgotPasswordRepo.findByOtpAndAdmin(otp, admin)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))) {
              forgotPasswordRepo.deleteById(fp.getFpid());
              return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        forgotPasswordRepo.deleteById(fp.getFpid());
        return ResponseEntity.ok("OTP has been verified!");
    }

//    @PostMapping("/changePassword/{email}")
//    public ResponseEntity<String> changePassword(@PathVariable String email,
//                                                 @RequestBody ChangePassword changePassword
////                                                 @RequestParam Integer otp
//    ) {
//        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
//            return new ResponseEntity<>("Passwords do not match. Please type again!", HttpStatus.EXPECTATION_FAILED);
//        }
//
//        String encodedPassword = adminProfileService.getMd5(changePassword.password());
//        System.out.println("Encoded Password: " + encodedPassword);
//        int result = adminProfileRepo.updatePassword(email, encodedPassword);
//        System.out.println("Update Result: " + result);
////
////        admin admin = adminProfileRepo.findByEmail(email)
////                .orElseThrow(() -> new AdminNotFoundException("Please provide an valid email!"));
////        ForgotPassword fp = forgotPasswordRepo.findByOtpAndAdmin(otp, admin)
////                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));
////        forgotPasswordRepo.deleteById(fp.getFpid());
//
//        return ResponseEntity.ok("Password has been changed!");
//    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email,
                                                 @RequestBody ChangePassword changePassword) {
        // Check if passwords match
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Passwords do not match. Please type again!", HttpStatus.EXPECTATION_FAILED);
        }

        // Encode password
        String encodedPassword = adminProfileService.getMd5(changePassword.password());
        System.out.println("Encoded Password: " + encodedPassword);

        // Update password
        int result = adminProfileRepo.updatePassword(email, encodedPassword);
        System.out.println("EMAIL: " + email);
        System.out.println("Update Result: " + result);

        // Return success response
        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
