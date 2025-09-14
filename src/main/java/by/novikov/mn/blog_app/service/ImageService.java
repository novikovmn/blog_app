package by.novikov.mn.blog_app.service;

import java.io.InputStream;
import java.util.Optional;

public interface ImageService {

    void upload(String imagePath, InputStream inputStream);
    Optional<byte[]> get (String imagePath);

}
