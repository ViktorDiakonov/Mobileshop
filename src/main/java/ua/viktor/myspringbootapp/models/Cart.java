package ua.viktor.myspringbootapp.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Diakonov Viktor
 */
@Getter
public class Cart {
    // Геттеры
    private final List<Phone> items = new ArrayList<>();

    public void addItem(Phone phone) {
        items.add(phone);
    }

    public double getTotal() {
        return items.stream().mapToDouble(Phone::getPrice).sum();
    }

    public void removeItem(int phoneId) {
        items.removeIf(phone -> phone.getId() == phoneId);
    }
}
