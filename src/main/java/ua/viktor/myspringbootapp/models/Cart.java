package ua.viktor.myspringbootapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author Diakonov Viktor
 */
@Getter
@AllArgsConstructor
@Setter
public class Cart {
    public static class CartItem {

        private final Phone phone;
        @Setter
        private int quantity;

        public CartItem(Phone phone, int quantity) {
            this.phone = phone;
            this.quantity = quantity;
        }

        // Геттеры
        public Phone getPhone() { return phone; }
        public int getQuantity() { return quantity; }
    }

    private final Map<Integer, CartItem> items = new HashMap<>();

    public void addItem(Phone phone) {
        items.compute(phone.getId(), (id, existingItem) ->
                existingItem == null
                        ? new CartItem(phone, 1)
                        : new CartItem(phone, existingItem.getQuantity() + 1)
        );
    }

    public void removeItem(int phoneId) {
        items.remove(phoneId);
    }

    public void updateQuantity(int phoneId, int quantity) {
        if (quantity <= 0) {
            removeItem(phoneId);
        } else {
            CartItem item = items.get(phoneId);
            if (item != null) {
                item.setQuantity(quantity);
            }
        }
    }

    public double getTotal() {
        return items.values().stream()
                .mapToDouble(item -> item.getPhone().getPrice() * item.getQuantity())
                .sum();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public boolean containsItem(int phoneId) {
        return items.containsKey(phoneId);
    }
}
