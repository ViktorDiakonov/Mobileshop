//package ua.viktor.myspringbootapp.repositories;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.annotation.Rollback;
//import ua.viktor.myspringbootapp.models.Order;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
//@Rollback(false)
//class OrderRepositoryTest {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    void testSaveOrder() {
//        Order order = new Order(
//                1, "Samsung", "S23", "128", 1000,
//                "Хочу отримати сьогодні", "Віктор", "380123456789",
//                "Київ", "/img/s23.jpg", 2, Date.from(Instant.now())
//        );
//
//        Order savedOrder = orderRepository.save(order);
//
//        assertThat(savedOrder).isNotNull();
//        assertThat(savedOrder.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    void testFindAllOrdersByDateDesc() {
//        List<Order> orders = orderRepository.findByOrderByDateDesc();
//
//        assertThat(orders).isNotEmpty();
//        assertThat(orders.get(0).getDate()).isAfterOrEqualTo(orders.get(orders.size() - 1).getDate());
//    }
//}
