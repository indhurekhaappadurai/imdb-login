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

    /**
     * Method to generate OTP
     * @param email
     * @return encodedOTP
     */
    public String generateOneTimePassword(String email) {
        int OTP = Integer.parseInt(new DecimalFormat("000000").format(new Random().nextInt(999999)));
        String encodedOTP = String.valueOf(OTP);
        return encodedOTP;
    }

    /**
     * Method to get EmailTemplate
     * @param registration
     * @return template
     * @throws IOException
     * @throws TemplateException
     */
    public String getEmailContent(Registration registration) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        String email = registration.getEmail();
        Map<String, Object> model = new HashMap<>();
        model.put("user", registration);
        configuration.getTemplate("/emailtemplate.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    /**
     * Method to send otp to email
     * @param registration
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     * @throws MessagingException
     */
    public void sendOtpEmail(Registration registration) throws MessagingException, IOException, TemplateException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome To IMDB Rating Project");
        helper.setTo(registration.getEmail());
        String emailContent = getEmailContent(registration);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    /**
     * Method to verify Email
     * @param registration
     * @return String message
     * @throws MessagingException
     * @throws TemplateException
     * @throws IOException
     */
    public String validate(Registration registration) throws MessagingException, TemplateException, IOException {
        if (registrationRepository.existsByEmail(registration.getEmail()) && registrationRepository.existsByOtp(registration.getOtp())) {
            return "User Registered Successfully";
        }
        else
        {
          registrationRepository.removeByEmail(registration.getEmail());
            return "Invalid Credential.SignUp with EmailId and UserName again";
        }
    }

    /**
     * Method to sign up account
     * @param registration
     * @return String message
     * @throws MessagingException
     * @throws TemplateException
     * @throws IOException
     */
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

    /**
     * Method to login with registered email,it will send otp to email.
     * @param registration
     * @return String message
     * @throws MessagingException
     * @throws TemplateException
     * @throws IOException
     */
    public String signIn(Registration registration) throws MessagingException, TemplateException, IOException {
        if (registrationRepository.existsByEmail(registration.getEmail())) {
            String otp = generateOneTimePassword(registration.getEmail());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String time = registration.setExpiration(timestamp.toString());
            registration.setOtp(otp);
            sendOtpEmail(registration);
            registrationRepository.updateOtp(registration.getEmail(),otp);
            return "Otp sent to your Registered Email";
        } else {
            return "User Not Registered";
        }
    }

    /**
     * Method to login with OTP
     * @param registration
     * @return String message
     * @throws MessagingException
     * @throws TemplateException
     * @throws IOException
     */
        public String LoginWithOtp(Registration registration) throws MessagingException, TemplateException, IOException {
            if (registrationRepository.existsByEmail(registration.getEmail()) && registrationRepository.existsByOtp(registration.getOtp())) {
                return "User Login Successfully";
            }
            else
            {
                return "Sign In Again";
            }
    }
}