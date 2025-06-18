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

    @GetMapping("/cart-order")
    public String cartOrder(@ModelAttribute("cart") Cart cart, Model model) {
        if (cart.getItems().isEmpty()) {
            return "redirect:/mobileshop/cart";
        }

        model.addAttribute("order", new Order());
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        return "person/cart-order";
    }

    @PostMapping("/cart-order-confirmation")
    public String placeOrder(@ModelAttribute("cart") Cart cart,
                             @ModelAttribute("order") @Valid Order order,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors() || cart.getItems().isEmpty()) {
            log.error("Cart order validation errors: {}", bindingResult.getAllErrors());
            model.addAttribute("cartItems", cart.getItems());
            model.addAttribute("total", cart.getTotal());
            return "person/cart-order";
        }

        List<Order> createdOrders = new ArrayList<>();
        for (Cart.CartItem item : cart.getItems()) {
            Order newOrder = getOrder(order, item); // здесь ты мапишь item в заказ
            phoneService.createOrder(newOrder);
            log.info("Создан новый заказ из корзины: {}", order);
            createdOrders.add(newOrder);
        }

        // вычисляем общую сумму
        int total = createdOrders.stream()
                .mapToInt(o -> o.getPrice() * o.getQuantity())
                .sum();

        cart.clear();

        model.addAttribute("orders", createdOrders);
        model.addAttribute("total", total);

        return "person/cart-order-confirmation";
    }


    private static Order getOrder(Order order, Cart.CartItem item) {
        Order newOrder = new Order();
        // Копируем все поля из формы
        newOrder.setPersonName(order.getPersonName());
        newOrder.setPersonPhone(order.getPersonPhone());
        newOrder.setPersonComment(order.getPersonComment()); // Добавлено
        newOrder.setPoint(order.getPoint());

        // Устанавливаем данные о товаре
        newOrder.setBrand(item.getPhone().getBrand());
        newOrder.setModel(item.getPhone().getModel());
        newOrder.setMemorySize(item.getPhone().getMemorySize());
        newOrder.setPrice(item.getPhone().getPrice());
        newOrder.setQuantity(item.getQuantity());
        newOrder.setImagePath(item.getPhone().getImagePath());

        return newOrder;
    }

    @GetMapping("/search")
    public String searchPhones(@RequestParam String q, Model model) {
        List<Phone> phones = phoneRepository.findByModelContainingIgnoreCase(q);
        model.addAttribute("phones", phones);
        model.addAttribute("query", q);
        return "person/search-results";
    }
}