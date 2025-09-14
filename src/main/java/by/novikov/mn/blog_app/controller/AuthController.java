package by.novikov.mn.blog_app.controller;

import by.novikov.mn.blog_app.dto.UserDto;
import by.novikov.mn.blog_app.entity.Role;
import by.novikov.mn.blog_app.entity.User;
import by.novikov.mn.blog_app.service.EmailService;
import by.novikov.mn.blog_app.service.UserService;
import by.novikov.mn.blog_app.util.mail.Mail;
import by.novikov.mn.blog_app.util.mail.MailNotifier;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MailNotifier mailNotifier;

    // return registration form

    @GetMapping("/register")
    public String registrationForm(@ModelAttribute("userDto") UserDto userDto) {
        return "registration-form";
    }

    // save user after registration

    @PostMapping("/register")
    public String register(@ModelAttribute("userDto") @Valid UserDto userDto,
                                                       BindingResult bindingResult,
                                                       Model model) throws MessagingException {

        // роль USER по умолчанию
        userDto.setRole(Role.USER);
        // есть ли уже такой юзер ?
        User existingUser = userService.findUserByUsername(userDto.getUsername());
        if (existingUser != null
                && existingUser.getUsername() != null
                && !existingUser.getUsername().isEmpty()) {
            bindingResult.rejectValue("username", "",
                    "User with this username is already exists!");
        }
        // валидация dto
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        // сохраним в бд
        userService.create(userDto);
        // ВЫСЛАТЬ УВЕДОМЛЕНИЕ НА EMAIL ОБ УСПЕШНОЙ РЕГИСТРАЦИИ
        mailNotifier.notify(userDto.getUsername(), mailNotifier.REGISTRATION_SUCCESS_SUBJECT, mailNotifier.REGISTRATION_SUCCESS_TEMPLATE);
        // перенаправим на страницу регистрации с параметром "success"
        return "redirect:/register?success";
    }

    // return login form

    @GetMapping("/login")
    public String loginFrom(){
        return "login";
    }





}
