package by.novikov.mn.blog_app.service;

import by.novikov.mn.blog_app.util.mail.Mail;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendThymeleafEmail(Mail mail, String templateName) throws MessagingException;

}
