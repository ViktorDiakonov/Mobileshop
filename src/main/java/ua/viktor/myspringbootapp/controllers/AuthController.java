//package ua.viktor.myspringbootapp.controllers;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import ua.viktor.myspringbootapp.models.Person;
//import ua.viktor.myspringbootapp.services.AuthService;
//import ua.viktor.myspringbootapp.util.PersonValidator;
//
//import javax.validation.Valid;
//
///**
// * @author Diakonov Viktor
// */
//@Slf4j
//@Controller
//@AllArgsConstructor
//@RequestMapping("/mobileshop/auth")
//public class AuthController {
//
//    private final AuthService authService;
//    private final PersonValidator personValidator;
//
//    //регистрация и сохранение пользователя------------------------------------
//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("person") Person person) {
//        log.info("Пользователь перешел на страницу регистрации");
//        return "auth/registration";
//    }
//
//    // save new user
//    @PostMapping("/registration")
//    public String performRegistrationPage(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
//        log.info("Пользователь пытается зарегистрироваться с данными: {}", person);
//        personValidator.validate(person, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            log.warn("Ошибка при регистрации пользователя: {}", bindingResult.getAllErrors());
//            return "auth/registration";
//        }
//
//        authService.register(person);
//        log.info("Пользователь успешно зарегистрирован: {}", person);
//
//        return "redirect:/mobileshop/auth/login";
//    }
//    //-----------------------------------------------------------------------
//
//    // user`s authentication page
//    @GetMapping("/login")
//    public String loginPage() {
//        log.info("Пользователь перешел на страницу входа");
//        return "auth/login";
//    }
//}
package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.services.AuthService;
import ua.viktor.myspringbootapp.util.PersonValidator;

import javax.validation.Valid;

/**
 * Контроллер для аутентификации и регистрации пользователей
 */
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/mobileshop/auth")
public class AuthController {

    private final AuthService authService;
    private final PersonValidator personValidator;
    private final AuthenticationManager authenticationManager;

    // Регистрация нового пользователя ------------------------------------
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        log.info("Пользователь перешел на страницу регистрации");
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistrationPage(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        log.info("Пользователь пытается зарегистрироваться с данными: {}", person);
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            log.warn("Ошибка при регистрации пользователя: {}", bindingResult.getAllErrors());
            return "auth/registration";
        }

        authService.register(person);
        log.info("Пользователь успешно зарегистрирован: {}", person);

        return "redirect:/mobileshop/auth/login";
    }
    //-----------------------------------------------------------------------

    // Страница входа пользователя
    @GetMapping("/login")
    public String loginPage() {
        log.info("Пользователь перешел на страницу входа");
        return "auth/login";
    }

    // Обработчик входа по номеру телефона
    @PostMapping("/process_login")
    public String processLogin(@RequestParam String phoneNumber, @RequestParam String password) {
        log.info("Пользователь с номером {} пытается войти", phoneNumber);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phoneNumber, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Пользователь {} успешно вошел", phoneNumber);

        return "redirect:/mobileshop/";
    }
}
