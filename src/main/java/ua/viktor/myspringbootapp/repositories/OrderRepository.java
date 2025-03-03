package ua.viktor.myspringbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.viktor.myspringbootapp.models.Order;

import java.util.List;

/**
 * @author Diakonov Viktor
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderByDateDesc();
}