package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
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
@AllArgsConstructor
@SessionAttributes("cart") // Только эта аннотация
@RequestMapping("/mobileshop")
public class CartController {

    private final PhoneService phoneService;

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart(); // Создаст корзину при первом обращении
    }

    @GetMapping("/cart/count")
    @ResponseBody
    public String getCartCount(@ModelAttribute("cart") Cart cart) {
        return String.valueOf(cart.getItems().size());
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
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @PathVariable int id,
            @ModelAttribute("cart") Cart cart
    ) {
        cart.removeItem(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("total", cart.getTotal());
        response.put("count", cart.getItems().size());
        return ResponseEntity.ok(response);
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
