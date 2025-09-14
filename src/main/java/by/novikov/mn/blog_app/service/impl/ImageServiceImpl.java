package by.novikov.mn.blog_app.service.impl;

import by.novikov.mn.blog_app.service.ImageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${images.path}")
    private String imagesDir;

    /*

        этот метод отвечает за сохранение файла по пути fullImagePath

    * */
    @Override
    @SneakyThrows
    public void upload (String imagePath, InputStream inputStream) {
        // формируем полный путь (именно сюда и сохраним фотку)
        Path fullImagePath = Path.of(imagesDir, imagePath);

        try (inputStream){
            // createDirectories() - для подстраховки (вдруг такой папки нет)
            // getParent() - для пути "c:\temp\file1.txt" возьмет "c:\temp"
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, inputStream.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    /*

     этот метод отвечает за получение файла по пути fullImagePath

 * */

    @Override
    @SneakyThrows
    public Optional<byte[]> get (String imagePath) {
        Path fullImagePath = Path.of(imagePath);
        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }
}
