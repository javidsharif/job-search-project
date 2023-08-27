//package com.practice.jobsearchproject.service.impl;
//
//import com.practice.jobsearchproject.exception.NotFoundException;
//import com.practice.jobsearchproject.model.entity.UserAuthentication;
//import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
//import com.practice.jobsearchproject.service.MailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//@Service
//@RequiredArgsConstructor
//public class MailServiceImpl implements MailService {
//    private final JavaMailSender javaMailSender;
//    private final UserAuthenticationRepository userAuthenticationRepository;
//
//    @Async
//    public void sendEmail(MimeMessage email) {
//        javaMailSender.send(email);
//    }
//
//    public MimeMessage createEmail(String email, String subject, String msg, String link) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//        String htmlMsg = msg + link;
//        // http://localhost:8080
//        // https://job-search-project-78cb5c6d6611.herokuapp.com
//        try {
//            helper.setText(htmlMsg, true);
//            helper.setTo(email);
//            helper.setSubject(subject);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//        return mimeMessage;
//    }
//
//    @Override
//    public String createResetPasswordEmail(String email) {
//        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", email)));
//
//        MimeMessage mimeMessage = createEmail(email,
//                "Reset password",
//                "<h3>Reset password link : </h3>",
//                "https://job-search-project-78cb5c6d6611.herokuapp.com/reset-passoword");
//        sendEmail(mimeMessage);
//        return "Reset password email sent!";
//    }
//}
