package ua.viktor.myspringbootapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.viktor.myspringbootapp.models.Cart;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.PhoneService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Diakonov Viktor
 */
@Controller
@SessionAttributes("cart") // Корзина хранится в сессии
public class CartController {

    @ModelAttribute("cart") // Инициализация корзины
    public Cart cart() {
        return new Cart();
    }

    private final PhoneService phoneService;

    @Autowired
    public CartController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("/cart/add/{phoneId}")
    public String addToCart(
            @PathVariable int phoneId,
            @ModelAttribute("cart") Cart cart,
            RedirectAttributes redirectAttributes
    ) {
        Phone phone = phoneService.findPhoneById(phoneId);

        if (!cart.containsItem(phoneId)) {
            cart.addItem(phone);
            redirectAttributes.addFlashAttribute("message", "Товар добавлен в корзину!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Товар уже в корзине");
        }

        return "redirect:/mobileshop/phone/" + phoneId;
    }

    @GetMapping("/cart")
    public String viewCart(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        return "person/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(
            @PathVariable int id,
            @ModelAttribute("cart") Cart cart,
            RedirectAttributes redirectAttributes
    ) {
        cart.removeItem(id);
        redirectAttributes.addFlashAttribute("message", "Товар видалено з корзини");
        return "redirect:/cart";
    }

    @GetMapping("/cart/total")
    @ResponseBody
    public String getCartTotal(@ModelAttribute("cart") Cart cart) {
        return String.valueOf(cart.getTotal());
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuantity(
            @RequestParam int phoneId,
            @RequestParam int quantity,
            @ModelAttribute("cart") Cart cart
    ) {
        cart.updateQuantity(phoneId, quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("total", cart.getTotal());
        response.put("items", cart.getItems().stream()
                .map(item -> Map.of(
                        "phone", Map.of(
                                "id", item.getPhone().getId(),
                                "brand", item.getPhone().getBrand(),
                                "model", item.getPhone().getModel(),
                                "memorySize", item.getPhone().getMemorySize(),
                                "price", item.getPhone().getPrice(),
                                "imagePath", item.getPhone().getImagePath()
                        ),
                        "quantity", item.getQuantity()
                ))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
