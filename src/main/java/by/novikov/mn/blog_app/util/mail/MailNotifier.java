package by.novikov.mn.blog_app.util.mail;

import by.novikov.mn.blog_app.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class MailNotifier {

    public final String REGISTRATION_SUCCESS_SUBJECT = "Registration successful";
    public final String DELETION_OF_ACCOUNT_SUBJECT = "Deletion of account";
    public final String REGISTRATION_SUCCESS_TEMPLATE = "notification-about-registration";
    public final String DELETION_OF_ACCOUNT_TEMPLATE = "notification-about-profile-deletion";

    private final EmailService emailService;

    public void notify(String email, String subject, String templateName) throws MessagingException {
        Mail mail = new Mail();
        mail.setTo(new String[]{email});
        mail.setSubject(subject);
        emailService.sendThymeleafEmail(mail, templateName);
    }
}
