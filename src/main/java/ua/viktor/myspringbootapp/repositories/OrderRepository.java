package ua.viktor.myspringbootapp.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.viktor.myspringbootapp.models.Order;
import java.util.List;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

//    @Override
//    @Query(value = "SELECT * FROM orders ORDER BY date DESC", nativeQuery = true)
//    List<Order> findAll();

    List<Order> findByOrderByDateDesc();

    // тест
//    @Query(value = "SELECT * FROM orders WHERE person_phone = ?", nativeQuery = true)
    List<Order> findOrdersByPersonPhone(String person_phone);
}