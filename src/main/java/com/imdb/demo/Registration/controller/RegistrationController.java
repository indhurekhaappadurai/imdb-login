package com.imdb.demo.Registration.controller;

import com.imdb.demo.Registration.entity.Registration;
import com.imdb.demo.Registration.service.MailService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/imdb")
public class RegistrationController {

    @Autowired
    MailService mailService;


    @PostMapping("/signUp")
    public String registerUser(@RequestBody Registration registration) throws MessagingException, IOException, TemplateException {
        return mailService.signUp(registration);
    }

    @PostMapping("/validate")
    public String ValidateUser(@RequestBody Registration registration) throws MessagingException, TemplateException, IOException {
        return mailService.validate(registration);
    }

    @PostMapping("/signIn")
    public String signIn(@RequestBody Registration registration){
        return mailService.signIn(registration);
    }
}
