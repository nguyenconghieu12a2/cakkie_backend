package com.cakkie.backend.service;

import com.cakkie.backend.dto.AdminMailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AdminEmailService {


    private final JavaMailSender javaMailSender;

    public AdminEmailService(JavaMailSender javaMailSenderr) {
        this.javaMailSender = javaMailSenderr;
    }


    public void sendSimpleMessage(AdminMailBody adminMailBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminMailBody.to());
        message.setFrom("hieuncce180986@fpt.edu.vn");
        message.setSubject(adminMailBody.subject());
        message.setText(adminMailBody.text());

        javaMailSender.send(message);
    }

}
