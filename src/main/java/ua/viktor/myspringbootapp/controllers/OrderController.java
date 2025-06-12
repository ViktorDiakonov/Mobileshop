package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.OrderService;
import ua.viktor.myspringbootapp.services.PhoneService;
import ua.viktor.myspringbootapp.util.OrderValidator;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@Controller
@SessionAttributes("cart") // Добавляем аннотацию
@AllArgsConstructor
@RequestMapping("/mobileshop")
public class OrderController {

    private final OrderService orderService;
    private final PhoneService phoneService;
    private final OrderValidator orderValidator;

    @InitBinder("order")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(orderValidator);
    }

    @GetMapping("/{id}/quick-order")
    public String showOrderForm(@PathVariable("id") int id, Model model) {
        Phone phone = phoneService.findPhoneById(id);
        Order order = new Order();
        // Заполняем данные из телефона
        order.setBrand(phone.getBrand());
        order.setModel(phone.getModel());
        order.setMemorySize(phone.getMemorySize());
        order.setPrice(phone.getPrice());
        order.setImagePath(phone.getImagePath());

        model.addAttribute("phone", phone);
        model.addAttribute("order", order);
        return "person/quick-order";
    }

    @PostMapping("/{id}/quick-order-confirmation")
    public String processOrder(@PathVariable("id") int id,
                               @ModelAttribute("order") @Valid Order order,
                               BindingResult bindingResult,
                               Model model) {

        Phone phone = phoneService.findPhoneById(id);

        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getAllErrors());
            model.addAttribute("phone", phone); // Добавляем телефон для повторного отображения формы
            return "person/quick-order";
        }

        // Заполняем данные из телефона
        order.setBrand(phone.getBrand());
        order.setModel(phone.getModel());
        order.setMemorySize(phone.getMemorySize());
        order.setPrice(phone.getPrice() * order.getQuantity()); // Учитываем количество
        order.setImagePath(phone.getImagePath());
        order.setDate(new Date());

        phoneService.createOrder(order);
        log.info("Создан новый заказ: {}", order);

        model.addAttribute("order", order);
        return "person/quick-order-confirmation";
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

            if (personOptional.isPresent()) {
                model.addAttribute("person", personOptional.get());
            }
        }

        return "person/my-orders";
    }
}
