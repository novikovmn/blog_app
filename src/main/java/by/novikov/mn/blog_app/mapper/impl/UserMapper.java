package by.novikov.mn.blog_app.mapper.impl;

import by.novikov.mn.blog_app.dto.UserDto;
import by.novikov.mn.blog_app.entity.User;
import by.novikov.mn.blog_app.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {

    @Value("${images.path}")
    private String imagesDir;

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    // для чтения из бд (READ)

    @Override
    public UserDto mapToDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .imagePath(entity.getImagePath())
                .commentDtos(
                        entity.getComments().stream()
                                    .map(commentMapper::mapToDto)
                                    .collect(Collectors.toList())
                ).postDtos(
                        entity.getPosts().stream()
                                .map(postMapper::mapToDto)
                                .collect(Collectors.toList())

                ).role(entity.getRole())
                .build();
    }

    // для обновления (UPDATE)

    public User mapToEntityForUpdate(UserDto userDto, User entityToUpdate) {
        entityToUpdate.setUsername(userDto.getUsername());
        entityToUpdate.setFirstname(userDto.getFirstname());
        entityToUpdate.setLastname(userDto.getLastname());

        // Не перезаписываем imagePath если новый файл не был загружен
        if (userDto.getImageFile() != null && !userDto.getImageFile().isEmpty()) {
            entityToUpdate.setImagePath(imagesDir + userDto.getImageFile().getOriginalFilename());
        }

        return entityToUpdate;
    }

    // для создания юзера (CREATE)

    @Override
    public User mapToEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .imagePath(imagesDir + userDto.getImageFile().getOriginalFilename())
                .build();
    }
}
