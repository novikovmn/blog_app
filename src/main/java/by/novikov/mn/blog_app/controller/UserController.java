package by.novikov.mn.blog_app.controller;

import by.novikov.mn.blog_app.dto.PostDto;
import by.novikov.mn.blog_app.dto.UserDto;
import by.novikov.mn.blog_app.service.EmailService;
import by.novikov.mn.blog_app.service.UserService;
import by.novikov.mn.blog_app.util.mail.Mail;
import by.novikov.mn.blog_app.util.mail.MailNotifier;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final MailNotifier mailNotifier;

    // SELECT ALL USERS (ADMIN)

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String users(Model model) {
        List<UserDto> userDtos = userService.findAll();
        model.addAttribute("users", userDtos);
        return "admin-users";
    }

    @GetMapping("/{userId}/profile")
    public String userProfilePage(@PathVariable("userId") Long userId,
                                  Model model) {

        UserDto userDto = userService.findById(userId);
        model.addAttribute("userDto", userDto);
        return "user-profile";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable("userId") Long userId,
                             @ModelAttribute("userDto") @Valid UserDto user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "user-profile";
        }
        userService.update(userId, user);
        return "redirect:/users/" + userId + "/profile";
    }


    @GetMapping("/{userId}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("userId") Long userId) {
        return userService.findAvatar(userId)
                .map(avatar -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .body(avatar)
                ).orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)::build);
    }

    @GetMapping("/{userId}/posts")
    public String userPosts(@PathVariable("userId") Long userId, Model model) {
        List<PostDto> postDtos = userService.findById(userId).getPostDtos();
        model.addAttribute("postDtos", postDtos);
        return "user-posts";
    }

    @GetMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId, HttpSession session) throws MessagingException {

        String currentUserRole = (String) session.getAttribute("CURRENT_USER_ROLE");
        Long currentUserId = (Long) session.getAttribute("CURRENT_USER_ID");
        String email = userService.findById(userId).getUsername();

        // если admin удаляет ЧУЖОЙ профиль
        if (currentUserRole.equals("ADMIN") && !currentUserId.equals(userId)) {
            // удалим юзера из бд
            userService.deleteUser(userId);
            // УВЕДОМЛЕНИЕ НА ПОЧТУ ОБ УДАЛЕНИИ АККАУНТА
            mailNotifier.notify(email, mailNotifier.DELETION_OF_ACCOUNT_SUBJECT, mailNotifier.DELETION_OF_ACCOUNT_TEMPLATE);
            // вернемся назад к админке с юзерами
            return "redirect:/users/admin";
        }

        // если юзер САМ удаляет свой профиль
        // сначала удалим сессию (т.е. произойдет logout)
        session.invalidate();
        // и удалим его из бд
        userService.deleteUser(userId);
        // УВЕДОМЛЕНИЕ НА ПОЧТУ ОБ УДАЛЕНИИ АККАУНТА
        mailNotifier.notify(email, mailNotifier.DELETION_OF_ACCOUNT_SUBJECT, mailNotifier.DELETION_OF_ACCOUNT_TEMPLATE);
        // затем его перекинет на главную
        return "redirect:/";
    }


    // search posts (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/search")
    public String searchUsersByAdmin(@RequestParam("query") String query, Model model) {
        List<UserDto> userDtos = userService.searchUsers(query);
        // перезатираем то, что было в "select all" (см. выше)
        // НА НАЙДЕННЫХ ЮЗЕРОВ
        // НАЗВАЕНИЕ АТРИБУТА НЕ МЕНЯЕМ = "users"
        model.addAttribute("users", userDtos);
        return "admin-users";
    }



}
