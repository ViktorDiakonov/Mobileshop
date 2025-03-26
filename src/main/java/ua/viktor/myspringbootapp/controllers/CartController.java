package ua.viktor.myspringbootapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.viktor.myspringbootapp.models.Cart;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.PhoneService;

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
            Phone phone = phoneService.findPhoneById(phoneId); // Получаем реальный телефон
            cart.addItem(phone);
            redirectAttributes.addFlashAttribute("message", "Товар добавлен в корзину!");
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
}
