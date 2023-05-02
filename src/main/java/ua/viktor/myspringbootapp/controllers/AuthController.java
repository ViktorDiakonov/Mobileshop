package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
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
@Controller
@AllArgsConstructor
@RequestMapping("/mobileshop/auth")
public class AuthController {

    private final AuthService authService;
    private final PersonValidator personValidator;

    // страница регистрации пользователя
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    // сохранение нового пользователя
    @PostMapping("/registration")
    public String performRegistrationPage(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) return "auth/registration";
        authService.register(person);

        return "redirect:/mobileshop/auth/login";
    }

    // страница аутентификации пользователя
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}