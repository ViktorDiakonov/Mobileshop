package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrdersByPersonName(String personName);

    List<Order> getOrdersByPersonPhoneNumber(String personPhone);
}
