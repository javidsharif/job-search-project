package com.practice.jobsearchproject.service.impl;

import com.practice.jobsearchproject.exception.NotFoundException;
import com.practice.jobsearchproject.model.entity.UserAuthentication;
import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
import com.practice.jobsearchproject.repository.VerificationTokenRepository;
import com.practice.jobsearchproject.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }

    public MimeMessage createEmail(UserAuthentication userAuthentication, String subject, String msg, String link) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = msg + link;
        // http://localhost:8080
        // https://job-search-project-78cb5c6d6611.herokuapp.com
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(userAuthentication.getEmail());
            helper.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return mimeMessage;
    }

    @Override
    public String createResetPasswordEmail(String email) {
        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("email with %s not found", email)));
        MimeMessage mimeMessage = createEmail(userAuthentication,
                "Reset password",
                "<h3>Reset password link : </h3>",
                "https://job-search-project-78cb5c6d6611.herokuapp.com/reset-passoword?token=");
//                "http://localhost:8080/reset-password?token=");
        sendEmail(mimeMessage);
        return "Reset password email sent! " +
                "Please, check your email to set new password to your account";
    }
}
