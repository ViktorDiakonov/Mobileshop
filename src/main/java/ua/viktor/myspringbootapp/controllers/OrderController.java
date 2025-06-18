package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Cart;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.PhoneService;
import ua.viktor.myspringbootapp.util.OrderValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
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

    private final PhoneService phoneService;
    private final OrderValidator orderValidator;

    @InitBinder("order")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(orderValidator);
    }

    // ========== ORDERS ==========

    // Quick order start
    @GetMapping("/{id}/quick-order")
    public String showOrderForm(@PathVariable("id") int id, Model model) {
        Phone phone = phoneService.findPhoneById(id);
        Order order = new Order();
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

        order.setBrand(phone.getBrand());
        order.setModel(phone.getModel());
        order.setMemorySize(phone.getMemorySize());
        order.setPrice(phone.getPrice() * order.getQuantity());
        order.setImagePath(phone.getImagePath());
        order.setDate(new Date());

        phoneService.createOrder(order);
        log.info("Создан новый заказ: {}", order);

        model.addAttribute("order", order);
        return "person/quick-order-confirmation";
    }
    // Quick order finish

    // Cart order start
//    @GetMapping("/cart-order")
//    public String cartOrder(@ModelAttribute("cart") Cart cart, Model model) {
//        if (cart.getItems().isEmpty()) {
//            return "redirect:/mobileshop/cart";
//        }
//
//        model.addAttribute("order", new Order());
//        model.addAttribute("cartItems", cart.getItems());
//        model.addAttribute("total", cart.getTotal());
//        return "person/cart-order";
//    }
//
//    @PostMapping("/cart-order-confirmation")
//    public String placeOrder(@ModelAttribute("cart") Cart cart,
//                             @ModelAttribute("order") @Valid Order order,
//                             BindingResult bindingResult,
//                             Model model) {
//
//        if (bindingResult.hasErrors() || cart.getItems().isEmpty()) {
//            log.error("Cart order validation errors: {}", bindingResult.getAllErrors());
//            model.addAttribute("cartItems", cart.getItems());
//            model.addAttribute("total", cart.getTotal());
//            return "person/cart-order";
//        }
//
//        List<Order> createdOrders = new ArrayList<>();
//        for (Cart.CartItem item : cart.getItems()) {
//            Order newOrder = getOrder(order, item); // здесь ты мапишь item в заказ
//            phoneService.createOrder(newOrder);
//            log.info("Создан новый заказ из корзины: {}", order);
//            createdOrders.add(newOrder);
//        }
//
//        // вычисляем общую сумму
//        int total = createdOrders.stream()
//                .mapToInt(o -> o.getPrice() * o.getQuantity())
//                .sum();
//
//        cart.clear();
//
//        model.addAttribute("orders", createdOrders);
//        model.addAttribute("total", total);
//
//        return "person/cart-order-confirmation";
//    }
//
//
//    private static Order getOrder(Order order, Cart.CartItem item) {
//        Order newOrder = new Order();
//        // Копируем все поля из формы
//        newOrder.setPersonName(order.getPersonName());
//        newOrder.setPersonPhone(order.getPersonPhone());
//        newOrder.setPersonComment(order.getPersonComment()); // Добавлено
//        newOrder.setPoint(order.getPoint());
//
//        // Устанавливаем данные о товаре
//        newOrder.setBrand(item.getPhone().getBrand());
//        newOrder.setModel(item.getPhone().getModel());
//        newOrder.setMemorySize(item.getPhone().getMemorySize());
//        newOrder.setPrice(item.getPhone().getPrice());
//        newOrder.setQuantity(item.getQuantity());
//        newOrder.setImagePath(item.getPhone().getImagePath());
//
//        return newOrder;
//    }
    // Cart order finish
}
