package ua.viktor.myspringbootapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.viktor.myspringbootapp.models.Cart;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class CartControllerAdvice {
    @ModelAttribute("cart")
    public Cart addCartToModel(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
