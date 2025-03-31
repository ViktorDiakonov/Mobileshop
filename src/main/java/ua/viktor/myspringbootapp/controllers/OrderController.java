package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.services.OrderService;

import java.util.List;

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

    @GetMapping("/my-orders")
    public String getUserOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {

            String personPhone = authentication.getName();

//            List<Order> orders = orderService.getOrdersByPersonName(username);
            List<Order> orders = orderService.getOrdersByPersonPhoneNumber(personPhone);
            model.addAttribute("orders", orders);
        }

        return "person/my-orders";
    }
}
