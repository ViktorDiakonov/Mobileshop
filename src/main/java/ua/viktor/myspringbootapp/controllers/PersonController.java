package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Cart;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.services.OrderService;
import ua.viktor.myspringbootapp.services.PhoneService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@Controller
@SessionAttributes("cart")
@AllArgsConstructor
@RequestMapping("/mobileshop")
public class PersonController {

    private final OrderService orderService;
    private final PhoneService phoneService;
    private final PhoneRepository phoneRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            model.addAttribute("username", authentication.getName());
        }
        List<Phone> phones = phoneService.findLast20Phones();
        model.addAttribute("phone", phones);
        log.info("Пользователь перешел на главную страницу магазина, отображаем 16 последних телефонов");
        return "person/main-page";
    }

    @GetMapping("/phones/{brand}")
    public String getPhonesByBrand(@PathVariable String brand,
                                   @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                   Model model) {
        log.info("Пользователь выбрал бренд: {}, сортировка по цене: {}", brand, sort);
        model.addAttribute("phone", phoneService.readPhonesByBrandSorted(brand, sort));
        String formattedBrand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
        model.addAttribute("selectedBrand", formattedBrand);
        return "person/list-models";
    }

    @GetMapping("/phone/{id}")
    public String viewPhoneById(@PathVariable("id") int id, Model model) {
        log.info("Запрос на просмотр телефона с id = {}", id);
        model.addAttribute("phone", phoneService.findPhoneById(id));
        return "person/show-phone";
    }

    @GetMapping("/my-orders")
    public String getUserOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {

            String phoneNumber = authentication.getName();
            List<Order> orders = orderService.getOrdersByPersonPhoneNumber(phoneNumber);
            Optional<Person> personOptional = phoneService.personPhoneNumber(phoneNumber);

            model.addAttribute("orders", orders);

            personOptional.ifPresent(person -> model.addAttribute("person", person));
        }
        return "person/my-orders";
    }

    @GetMapping("/contacts")
    public String contacts() {
        log.info("Пользователь перешел на страницу 'Контакты'");
        return "person/contacts";
    }

    @GetMapping("/search")
    public String searchPhones(@RequestParam String q, Model model) {
        List<Phone> phones = phoneRepository.findByModelContainingIgnoreCase(q);
        model.addAttribute("phones", phones);
        model.addAttribute("query", q);
        return "person/search-results";
    }
}