package by.novikov.mn.blog_app.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DirCreator {

    @Value("${images.path}")
    private String avatarDir;

    @PostConstruct
    public void init() throws Exception {
        Path path = Paths.get(avatarDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
