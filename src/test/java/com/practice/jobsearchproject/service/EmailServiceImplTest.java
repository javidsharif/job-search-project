//package com.practice.jobsearchproject.service;
//
//import com.practice.jobsearchproject.model.entity.UserAuthentication;
//import com.practice.jobsearchproject.repository.UserAuthenticationRepository;
//import com.practice.jobsearchproject.repository.VerificationTokenRepository;
//import com.practice.jobsearchproject.service.impl.EmailServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.MimeMessage;
//import java.util.Optional;
//
//
//import com.practice.jobsearchproject.config.security.SecurityConfig;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.context.annotation.Import;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//@ExtendWith(MockitoExtension.class)
//@Import(SecurityConfig.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@RequiredArgsConstructor
//public class EmailServiceImplTest {
//    @Mock
//    private JavaMailSender javaMailSender;
//    @Mock
//    private UserAuthenticationRepository userAuthenticationRepository;
//    @Mock
//    private VerificationTokenRepository verificationTokenRepository;
//    @InjectMocks
//    private EmailServiceImpl emailService;
//
//    @Test
//    public void testCreateResetPasswordEmail() {
//        String email = "test@example.com";
//        UserAuthentication userAuthentication = new UserAuthentication();
//        userAuthentication.setEmail(email);
//        Mockito.when(userAuthenticationRepository.findByEmail(email)).thenReturn(Optional.of(userAuthentication));
//
//        Mockito.doReturn(new MimeMessage((Session) null)).when(emailService).createEmail(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
//        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) Mockito.any()));
//        String result = emailService.createResetPasswordEmail(email);
//
//        Assertions.assertNotNull(result);
////        Assertions.assertEquals("Reset password email sent! Please, check your email to set new password to your account", result);
//        Assertions.assertTrue(result.contains("Reset password email sent"));
//        Mockito.verify(javaMailSender, Mockito.times(1)).send(Mockito.any(MimeMessage.class));
//    }
//
//    @Test
//    public void testCreateResetPasswordEmail_UserNotFound() {
//        String email = "nonexistent@example.com";
//        Mockito.when(userAuthenticationRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        emailService.createResetPasswordEmail(email);
//    }
//
//    @Test
//    public void testSendEmail() throws MessagingException {
//        String to = "test@example.com";
//        String subject = "Test Subject";
//        String content = "Test Content";
//
//        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//
//        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
////        whenNew(MimeMessageHelper.class).withArguments(mimeMessage).thenReturn(helper);
//
////        emailService.sendEmail(to, subject, content);
//
//        Mockito.verify(helper).setTo(to);
//        Mockito.verify(helper).setSubject(subject);
//        Mockito.verify(helper).setText(content, true);
//
//        Mockito.verify(javaMailSender).send(mimeMessage);
//    }
//}
