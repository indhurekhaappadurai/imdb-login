package com.imdb.demo.Registration.service;

import com.imdb.demo.Registration.entity.Registration;
import com.imdb.demo.Registration.repository.RegistrationRepository;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration configuration;

    @Autowired
    RegistrationRepository registrationRepository;

    public String generateOneTimePassword(String email) {
        int OTP = Integer.parseInt(new DecimalFormat("000000").format(new Random().nextInt(999999)));
        String encodedOTP = String.valueOf(OTP);
        return encodedOTP;
    }
    public String getEmailContent(Registration registration) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        String email = registration.getEmail();
        Map<String, Object> model = new HashMap<>();
        model.put("user", registration);
        configuration.getTemplate("/emailtemplate.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    public void sendOtpEmail(Registration registration) throws MessagingException, IOException, TemplateException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome To IMDB Rating Project");
        helper.setTo(registration.getEmail());
        String emailContent = getEmailContent(registration);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    public String validate(Registration registration) throws MessagingException, TemplateException, IOException {
        if (registrationRepository.existsByEmail(registration.getEmail()) && registrationRepository.existsByOtp(registration.getOtp())) {
            String otp = generateOneTimePassword(registration.getEmail());
            registration.setOtp(otp);
            registrationRepository.updateOtp(registration.getEmail(),otp);
            sendOtpEmail(registration);
            return ("User Registered Successfully"+"\r\n"+"SignIn with this OTP that has been sent to the registered Email ID");
        }
        else
        {
            registrationRepository.removeByEmail(registration.getEmail());
            return "SignUp with EmailId and UserName again";
        }
    }

    public String signUp(Registration registration) throws MessagingException, TemplateException, IOException {
        if (registrationRepository.existsByEmail(registration.getEmail())){
            return "Email Already Exist";
        }
        String otp = generateOneTimePassword(registration.getEmail());
        registration.setOtp(otp);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = registration.setExpiration(timestamp.toString());
        sendOtpEmail(registration);
        Registration user = new Registration(registration.getName(), registration.getEmail(), registration.getOtp(), time);
        registrationRepository.save(user);
        return "An OTP has been sent to the registered Email ID";
    }

    public String signIn(Registration registration){
        if (registrationRepository.existsByEmail(registration.getEmail()) && registrationRepository.existsByOtp(registration.getOtp())) {
            return "User LoggedIn Successfully";
        }
        else
        {
            return "Invalid EmailId or OTP .";
        }
    }
}