package com.cakkie.backend.controller.adminForgotPassword;

import com.cakkie.backend.dto.adminLogin.AdminMailBody;
import com.cakkie.backend.exception.adminLogin.AdminNotFoundException;
import com.cakkie.backend.model.ForgotPassword;
import com.cakkie.backend.model.admin;
import com.cakkie.backend.repository.adminProfile.AdminProfileRepo;
import com.cakkie.backend.repository.adminLogin.AdminForgotPasswordRepo;
import com.cakkie.backend.service.adminProfile.AdminProfileService;
import com.cakkie.backend.service.adminLogin.AdminEmailService;
import com.cakkie.backend.utils.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/admin/forgotPassword")
public class AdminForgotPasswordController {

    private final AdminProfileRepo adminProfileRepo;

    private final AdminProfileService adminProfileService;

    private final AdminEmailService adminEmailService;

    private final AdminForgotPasswordRepo adminForgotPasswordRepo;

    public AdminForgotPasswordController(AdminProfileRepo adminProfileRepo, AdminProfileService adminProfileService, AdminEmailService adminEmailService, AdminForgotPasswordRepo adminForgotPasswordRepo) {
        this.adminProfileRepo = adminProfileRepo;
        this.adminProfileService = adminProfileService;
        this.adminEmailService = adminEmailService;
        this.adminForgotPasswordRepo = adminForgotPasswordRepo;
    }

    //send mail for email verification
    @PostMapping("/verifyEmail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        admin admin = adminProfileRepo.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException("Please provide an valid email!"));

        int otp = otpGenerator();

        AdminMailBody adminMailBody = AdminMailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot Password request: " + otp)
                .subject("OTP for Forgot password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .admin(admin)
                .build();

        adminEmailService.sendSimpleMessage(adminMailBody);

        adminForgotPasswordRepo.save(fp);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        admin admin = adminProfileRepo.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException("Please provide an valid email!"));
        ForgotPassword fp = adminForgotPasswordRepo.findByOtpAndAdmin(otp, admin)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + email));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))) {
              adminForgotPasswordRepo.deleteById(fp.getFpid());
              return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        adminForgotPasswordRepo.deleteById(fp.getFpid());
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
