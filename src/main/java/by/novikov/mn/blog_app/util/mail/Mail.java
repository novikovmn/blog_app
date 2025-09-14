package by.novikov.mn.blog_app.util.mail;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {

    private String[] to; // для нескольких получателей
    private String subject; // тема
    private String body;    // контент
}
