package ua.viktor.myspringbootapp.util.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PhoneNotFoundException.class)
    public String handlePhoneNotFoundException(Model model) {
        model.addAttribute("errorMessage", "Телефон не знайдено.");
        return "error/phone-not-found";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(Model model) {
        model.addAttribute("errorMessage", "Заказ не знайдено.");
        return "error/order-not-found";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Model model) {
        model.addAttribute("errorMessage", "Сторінку не знайдено. ");
        return "error/404";
    }
}
