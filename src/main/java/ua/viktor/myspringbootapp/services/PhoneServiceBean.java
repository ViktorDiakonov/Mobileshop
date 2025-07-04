package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.OrderRepository;
import ua.viktor.myspringbootapp.repositories.PeopleRepository;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.util.exception.PhoneNotFoundException;

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
    private final PeopleRepository peopleRepository;

    public Phone findPhoneById(int id) {
        log.info("Запрос на поиск телефона с id = {}", id);
        Optional<Phone> foundPhone = phoneRepository.findById(id);
        if (foundPhone.isPresent()) {
            log.info("Телефон с id = {} найден", id);
        } else {
            log.warn("Телефон с id = {} не найден", id);
        }
        return foundPhone.orElseThrow(PhoneNotFoundException::new);
    }

    @Transactional
    @Override
    public void createOrder(Order order) {
        log.info("Создан новый заказ: {}", order);
        orderRepository.save(order);
    }

    @Override
    public List<Phone> findLast20Phones() {
        log.info("Запрос на просмотр 16 последних телефонов через SQL LIMIT");
        return phoneRepository.findTop20ByOrderByIdDesc();
    }

    @Override
    public List<Phone> readPhonesByBrandSorted(String brand, String sort) {
        log.info("Запрос телефонов по бренду: {}, сортировка: {}", brand, sort);
        String formattedBrand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
        if ("desc".equalsIgnoreCase(sort)) {
            return phoneRepository.findByBrandOrderByPriceDesc(formattedBrand);
        }
        return phoneRepository.findByBrandOrderByPriceAsc(formattedBrand);
    }

    @Override
    public Optional<Person> personPhoneNumber(String phoneNumber) {
        return peopleRepository.findByPhoneNumber(phoneNumber);
    }
}