package by.novikov.mn.blog_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "by.novikov.mn.blog_app")
public class BlogAppApplication {


    public static void main(String[] args) {

        SpringApplication.run(BlogAppApplication.class, args);


    }

}
