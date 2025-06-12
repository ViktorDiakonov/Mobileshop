package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Order;

import javax.validation.Valid;
import java.util.List;

public interface OrderService {

    List<Order> getOrdersByPersonName(String personName);

    List<Order> getOrdersByPersonPhoneNumber(String personPhone);

    void save(@Valid Order order);

}
