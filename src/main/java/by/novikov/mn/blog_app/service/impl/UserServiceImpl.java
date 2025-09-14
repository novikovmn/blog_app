package by.novikov.mn.blog_app.service.impl;

import by.novikov.mn.blog_app.dto.UserDto;
import by.novikov.mn.blog_app.entity.User;
import by.novikov.mn.blog_app.mapper.impl.UserMapper;
import by.novikov.mn.blog_app.repository.UserRepository;
import by.novikov.mn.blog_app.service.ImageService;
import by.novikov.mn.blog_app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserDto findById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToDto)
                .get();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(UserDto userDto) {
         Optional.of(userDto)
                // шифруем пароль
                 .map(dto ->{
                     String encodedPassword = passwordEncoder.encode(dto.getPassword());
                     dto.setPassword(encodedPassword);
                     return dto;
                 })
                 .map(userDtoWithEncodedPassword -> {
                            // сохраняем аватарку в корне проекта (./images/)
                            uploadImage(userDtoWithEncodedPassword.getImageFile());
                            // из dto получаем User
                            return userMapper.mapToEntity(userDtoWithEncodedPassword);
                        }
                        // сохраняем User в бд
                ).map(userRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void update(Long id, UserDto userDto) {
        // по id получим User, которого нужно обновить
         userRepository.findById(id)
                .map(user -> {
                    // сохраним новую аватарку в корне проекта
                            uploadImage(userDto.getImageFile());
                    // обновим найденного User на основании данных из userCreateEditDto
                            return userMapper.mapToEntityForUpdate(userDto, user);
                        }
                        // сохраним User в бд
                ).map(userRepository::saveAndFlush);
    }



    @Override
    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImagePath)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        return userRepository.searchUsers(query).stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
