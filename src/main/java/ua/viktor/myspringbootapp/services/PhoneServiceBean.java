package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.OrderRepository;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.util.exeption.PhoneNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@AllArgsConstructor
@Service
public class PhoneServiceBean implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final OrderRepository orderRepository;

//    @Override
//    public List<Phone> readAllPhonesByBrandApple(String phone) {
//        log.info("Просмотр телефонов бренда = {}", phone);
//        return phoneRepository.findByBrandOrderByModelAsc("Apple");
//    }
//
//    @Override
//    public List<Phone> readAllPhonesByBrandXiaomi(String phone) {
//        log.info("Просмотр телефонов бренда = {}", phone);
//        return phoneRepository.findByBrandOrderByModelAsc("Xiaomi");
//    }
//
//    @Override
//    public List<Phone> readAllPhonesByBrandSamsung(String phone) {
//        log.info("Просмотр телефонов бренда = {}", phone);
//        return phoneRepository.findByBrandOrderByModelAsc("Samsung");
//    }

    public Phone findPhone(int id) {
        log.info("Запрос - найти телефон с id = {} начало", id);
        Optional<Phone> foundPhone = phoneRepository.findById(id);
        log.info("Запрос - найти телефон с id = {} конец", id);
        return foundPhone.orElseThrow(PhoneNotFoundException::new);
    }

    @Override
    public void createOrder(Order order) {
        log.info("Создан новый заказ: order = {}", order);
        orderRepository.save(order);
    }

    // тест
    @Override
    public List<Order> readAllOrdersByPersonPhone(String phone) {
        return orderRepository.findOrdersByPersonPhone(phone);
    }

    @Override
    public List<Phone> findAllPhones() {
        log.info("Просмотр всех телефонов");
        return phoneRepository.findAll();
    }

    @Override
    public List<Phone> readPhonesByBrandSorted(String brand, String sort) {
        String formattedBrand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
        if ("asc".equalsIgnoreCase(sort)) {
            return phoneRepository.findByBrandOrderByPriceDesc(formattedBrand);
        }
        return phoneRepository.findByBrandOrderByPriceAsc(formattedBrand);
    }
}

