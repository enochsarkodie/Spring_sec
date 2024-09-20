package com.example.mySpringProject.service;

import com.example.mySpringProject.emailTemplateName.EmailTemplateName;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import jakarta.mail.internet.MimeMessage;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${email_sender")
    private String EMAIL_SENDER;


    @Async
    public void sendConfirmationEmail(String to,
                          String username,
                          EmailTemplateName emailTemplateName,
                          String confirmationUrl,
                          String activationCode,
                          String subject ) throws MessagingException {
        String templateName;
        if(emailTemplateName == null){
            templateName = "confirm-email";
        }else {
            templateName = emailTemplateName.name();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String,Object> properties = new HashMap<>();
        properties.put("username",username);
        properties.put("confirmationUrl",confirmationUrl);
        properties.put("activation_code",activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(EMAIL_SENDER);
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName,context);

        helper.setText(template,true);

        mailSender.send(mimeMessage);

    }

    public void sendEmail(EmailTemplateName templateName,
                          String emailReceiver,
                          String subject,
                          Map<String, Object> variables) throws MessagingException{

        Context context = new Context();
        context.setVariables(variables);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        helper.setPriority(1);
        helper.setSubject(subject);
        helper.setFrom(EMAIL_SENDER);
        helper.setTo(emailReceiver);

        String template = templateEngine.process(String.valueOf(templateName), context);
        helper.setText(template,true);
        mailSender.send(mimeMessage);


    }


}
