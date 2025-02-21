package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.services.AuthService;
import ua.viktor.myspringbootapp.util.PersonValidator;

import javax.validation.Valid;
/**
 * @author Diakonov Viktor
 */
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/mobileshop/auth")
public class AuthController {

    private final AuthService authService;
    private final PersonValidator personValidator;

    // user`s registration page
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        log.info("Пользователь перешел на страницу регистрации");
        return "auth/registration";
    }

    // save new user
    @PostMapping("/registration")
    public String performRegistrationPage(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        log.info("Пользователь пытается зарегистрироваться с данными: {}", person);
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            log.warn("Ошибка при регистрации пользователя: {}", bindingResult.getAllErrors());
            return "auth/registration";
        }

        authService.register(person);
        log.info("Пользователь успешно зарегистрирован: {}", person);

        return "redirect:/mobileshop/auth/login";
    }

    // user`s authentication page
    @GetMapping("/login")
    public String loginPage() {
        log.info("Пользователь перешел на страницу входа");
        return "auth/login";
    }
}