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
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.services.PhoneService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@Controller
@SessionAttributes("cart") // Добавляем аннотацию
@AllArgsConstructor
@RequestMapping("/mobileshop")
public class PersonController {

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

    // show specific phone
    @GetMapping("/phone/{id}")
    public String viewPhoneById(@PathVariable("id") int id, Model model) {
        log.info("Запрос на просмотр телефона с id = {}", id);
        model.addAttribute("phone", phoneService.findPhoneById(id));
        return "person/show-phone";
    }

    //----------------------------------------------------------------------
    // order creation page
    @GetMapping("/{id}/new_order")
    public String createNewOrder(@PathVariable("id") int id, Model model) {
        log.info("Пользователь перешел на страницу создания заказа для телефона с id = {}", id);
        model.addAttribute("phone", phoneService.findPhoneById(id));
        return "person/order";
    }

    @PostMapping("/{id}/order")
    public String createOrder(@PathVariable int id,
                              @RequestParam("quantity") int quantity,
                              @ModelAttribute("order") @Valid Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Ошибка при создании заказа для телефона с id = {}", id);
            return "redirect:/mobileshop/" + id;
        }

        Phone phone = phoneService.findPhoneById(id);
        if (phone == null) {
            log.error("Телефон с id = {} не найден, перенаправляем на главную страницу", id);
            return "redirect:/mobileshop";
        }

        order.setBrand(phone.getBrand());
        order.setModel(phone.getModel());
        order.setMemorySize(phone.getMemorySize());
        order.setPrice(phone.getPrice() * quantity);
        order.setImagePath(phone.getImagePath());
        order.setQuantity(quantity);

        phoneService.createOrder(order);
        log.info("Создан новый заказ: {}", order);
        model.addAttribute("order", order);
        return "person/buy";
    }
//---------------------------------------------------------------------

    // returns the page "About us"
    @GetMapping("/aboutus")
    public String aboutUs() {
        log.info("Пользователь перешел на страницу 'О нас'");
        return "person/aboutus";
    }

    // returns the page "Contacts"
    @GetMapping("/contacts")
    public String contacts() {
        log.info("Пользователь перешел на страницу 'Контакты'");
        return "person/contacts";
    }

    // returns the page "Conditions of delivery"
    @GetMapping("/delivery")
    public String delivery() {
        log.info("Пользователь перешел на страницу 'Условия доставки'");
        return "person/delivery";
    }

    @GetMapping("/checkout")
    public String checkout(@ModelAttribute("cart") Cart cart, Model model) {
        if (cart.getItems().isEmpty()) {
            return "redirect:/mobileshop/cart";
        }

        model.addAttribute("order", new Order());
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        return "person/cart-order";
    }

//    @PostMapping("/place-order")
//    public String placeOrder(@ModelAttribute("cart") Cart cart,
//                             @ModelAttribute("order") @Valid Order order,
//                             BindingResult bindingResult,
//                             Model model) {
//
//        if (bindingResult.hasErrors() || cart.getItems().isEmpty()) {
//            model.addAttribute("cartItems", cart.getItems());
//            model.addAttribute("total", cart.getTotal());
//            return "person/cart-order";
//        }
//
//        // Создаем заказ для каждого товара
//        List<Order> createdOrders = new ArrayList<>();
//        for (Cart.CartItem item : cart.getItems()) {
//            Order newOrder = getOrder(order, item);
//
//            phoneService.createOrder(newOrder);
//            createdOrders.add(newOrder);
//        }
//
//        // Очищаем корзину
//        cart.clear();
//
//        model.addAttribute("orders", createdOrders);
//        return "person/cart-order-success";
//    }
@PostMapping("/place-order")
public String placeOrder(@ModelAttribute("cart") Cart cart,
                         @ModelAttribute("order") @Valid Order order,
                         BindingResult bindingResult,
                         Model model) {

    if (bindingResult.hasErrors() || cart.getItems().isEmpty()) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        return "person/cart-order";
    }

    List<Order> createdOrders = new ArrayList<>();
    for (Cart.CartItem item : cart.getItems()) {
        Order newOrder = getOrder(order, item); // здесь ты мапишь item в заказ
        phoneService.createOrder(newOrder);
        createdOrders.add(newOrder);
    }

    // Вот эта строка вычисляет общую сумму
    int total = createdOrders.stream()
            .mapToInt(o -> o.getPrice() * o.getQuantity())
            .sum();

    cart.clear();

    model.addAttribute("orders", createdOrders);
    model.addAttribute("total", total);

    return "person/cart-order-success";
}


    private static Order getOrder(Order order, Cart.CartItem item) {
        Order newOrder = new Order();
        // Копируем все поля из формы
        newOrder.setPersonName(order.getPersonName());
        newOrder.setPersonPhone(order.getPersonPhone());
        newOrder.setPersonComment(order.getPersonComment()); // Добавлено
        newOrder.setPoint(order.getPoint()); // Теперь должно работать

        // Устанавливаем данные о товаре
        newOrder.setBrand(item.getPhone().getBrand());
        newOrder.setModel(item.getPhone().getModel());
        newOrder.setMemorySize(item.getPhone().getMemorySize());
//        newOrder.setPrice(item.getPhone().getPrice() * item.getQuantity());
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