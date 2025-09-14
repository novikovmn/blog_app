package by.novikov.mn.blog_app.service;

import by.novikov.mn.blog_app.dto.UserDto;
import by.novikov.mn.blog_app.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findUserByUsername(String username);
    UserDto findById(Long userId);
    List<UserDto> findAll();
    void create(UserDto userDto);
    void update(Long id, UserDto userDto);
    Optional<byte[]> findAvatar(Long id);
    void deleteUser(Long userId);
    List<UserDto> searchUsers(String query);

}
