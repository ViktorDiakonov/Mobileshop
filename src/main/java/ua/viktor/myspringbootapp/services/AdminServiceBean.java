package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.OrderRepository;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.util.exeption.OrderNotFoundException;
import ua.viktor.myspringbootapp.util.exeption.PhoneNotFoundException;

import java.util.List;
import java.util.Optional;
/**
 * @author Diakonov Viktor
 */
@Slf4j
@AllArgsConstructor
@Service
public class AdminServiceBean implements AdminService{

    private final OrderRepository orderRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public void deletePhoneById(Integer id) {
        log.info("Попытка удаления телефона с id = {}", id);
        Phone phone = phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
        phoneRepository.delete(phone);
        log.info("Телефон с id = {} успешно удален", id);
    }

    @Override
    public void deleteOrderById(Integer id) {
        log.info("Попытка удаления заказа с id = {}", id);
        orderRepository.deleteById(id);
        log.info("Заказ с id = {} успешно удален", id);
    }

    @Override
    public Order findOrder(int id) {
        log.info("Поиск заказа с id = {}", id);
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Phone create(Phone phone) {
        log.info("Попытка создания нового телефона: {}", phone);
        return phoneRepository.save(phone);
    }

    @Override
    public void updateById(Integer id, Phone updatedPhone) {
        log.info("Попытка обновления телефона с id = {}", id);
        Phone phoneToBeUpdated = readById(id);
        phoneToBeUpdated.setBrand(updatedPhone.getBrand());
        phoneToBeUpdated.setModel(updatedPhone.getModel());
        phoneToBeUpdated.setMemorySize(updatedPhone.getMemorySize());
        phoneToBeUpdated.setPrice(updatedPhone.getPrice());
        phoneRepository.save(phoneToBeUpdated);
        log.info("Телефон с id = {} успешно обновлен", id);
    }

    @Override
    public Phone readById(Integer id) {
        log.info("Попытка получения телефона с id = {}", id);
        return phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
    }

    @Override
    public List<Order> findAllOrders() {
        log.info("Просмотр всех заказов");
        List<Order> orders = orderRepository.findByOrderByDateDesc();
        log.info("Найдено {} заказов", orders.size());
        return orders;
    }

    @Override
    public List<Order> findWithPagination(int page, int size) {
        log.info("Просмотр заказов с пагинацией: страница = {}, размер = {}", page, size);
        List<Order> orders = orderRepository.findAll(PageRequest.of(page, size, Sort.by("date").descending())).getContent();
        log.info("Найдено {} заказов на странице {}", orders.size(), page);
        return orders;
    }

}
