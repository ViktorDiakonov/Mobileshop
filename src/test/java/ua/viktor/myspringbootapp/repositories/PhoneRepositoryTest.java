//package ua.viktor.myspringbootapp.repositories;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
//import org.springframework.test.context.ContextConfiguration;
//import ua.viktor.myspringbootapp.models.Phone;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// * @author Diakonov Viktor
// */
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
//class PhoneRepositoryTest {
//
//    @Autowired
//    private PhoneRepository phoneRepository;
//
//    @BeforeEach
//    void setUp() {
//        phoneRepository.deleteAll();
//
//        phoneRepository.save(new Phone("/img/s23.jpg", 1, "Samsung", "S23", "128", 1000));
//        phoneRepository.save(new Phone("/img/iphone14.jpg", 2, "Apple", "iPhone 14", "128", 1200));
//        phoneRepository.save(new Phone("/img/s22.jpg", 3, "Samsung", "S22", "256", 800));
//        phoneRepository.save(new Phone("/img/iphone13.jpg", 4, "Apple", "iPhone 13", "256", 1100));
//    }
//
//    @Test
//    void findByBrandOrderByPriceAsc_ShouldReturnPhonesInAscendingOrder() {
//        List<Phone> phones = phoneRepository.findByBrandOrderByPriceAsc("Samsung");
//
//        assertThat(phones).hasSize(2);
//        assertThat(phones.get(0).getModel()).isEqualTo("S22");
//        assertThat(phones.get(1).getModel()).isEqualTo("S23");
//    }
//
//    @Test
//    void findByBrandOrderByPriceDesc_ShouldReturnPhonesInDescendingOrder() {
//        List<Phone> phones = phoneRepository.findByBrandOrderByPriceDesc("Apple");
//
//        assertThat(phones).hasSize(2);
//        assertThat(phones.get(0).getModel()).isEqualTo("iPhone 14");
//        assertThat(phones.get(1).getModel()).isEqualTo("iPhone 13");
//    }
//
//    @Test
//    void findTop16ByOrderByIdDesc_ShouldReturnPhones() {
//        List<Phone> phones = phoneRepository.findTop20ByOrderByIdDesc();
//
//        assertThat(phones).hasSize(4); // в setUp 4 телефона
//        assertThat(phones.get(0).getModel()).isEqualTo("iPhone 13");
//        assertThat(phones.get(3).getModel()).isEqualTo("S23");
//    }
//}
