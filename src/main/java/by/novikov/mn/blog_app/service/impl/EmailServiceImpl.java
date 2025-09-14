package by.novikov.mn.blog_app.service.impl;

import by.novikov.mn.blog_app.service.EmailService;
import by.novikov.mn.blog_app.util.mail.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;      // email для рассылки

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    @Override
    public void sendThymeleafEmail(Mail mail, String templateName) throws MessagingException {
        for(String recipient : mail.getTo()){
            // recipient - это строка-email
            // хранится в контексте таймлифа под ключом "recipient"
            Context context = new Context();
            context.setVariable("recipient", recipient);
            // результат обработки контекста Thymeleaf - сгенерированный html-код
            // со всеми подставленными значениями
            // templateName - html-страница в папке "templates"
            String process = templateEngine.process(templateName, context);
            //-----
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(new InternetAddress(sender));
            // true - сгенерированный текст явл. html-кодом
            mimeMessageHelper.setText(process, true);
            mimeMessageHelper.setTo(recipient);
            //-----
            mailSender.send(mimeMessage);
        }
    }



}
