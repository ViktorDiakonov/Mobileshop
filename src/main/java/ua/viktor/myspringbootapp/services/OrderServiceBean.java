package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.repositories.OrderRepository;

import java.util.List;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@AllArgsConstructor
@Service
public class OrderServiceBean implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> getOrdersByPersonName(String personName) {
        return orderRepository.findByPersonName(personName);
    }
}
