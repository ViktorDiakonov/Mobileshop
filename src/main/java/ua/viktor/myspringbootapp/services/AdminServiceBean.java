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
        log.info("Удален телефон с id = {}", id);
        Phone phone = phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
        phoneRepository.delete(phone);
    }

    @Override
    public void deleteOrderById(Integer id) {
        log.info("Удален Заказ с id = {}", id);
        orderRepository.deleteById(id);
    }

    @Override
    public Order findOrder(int id) {
        log.info("Выведен заказ с id = {}", id);
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Phone create(Phone phone) {
        log.info("Создан новый телефон = {}", phone);
        return phoneRepository.save(phone);
    }

    @Override
    public void updateById(Integer id, Phone updatedPhone) {
        log.info("Обновлен телефон с Id = {}", id);
        Phone phoneToBeUpdated = readById(id);
        phoneToBeUpdated.setBrand(updatedPhone.getBrand());
        phoneToBeUpdated.setModel(updatedPhone.getModel());
        phoneToBeUpdated.setMemorySize(updatedPhone.getMemorySize());
        phoneToBeUpdated.setPrice(updatedPhone.getPrice());
        phoneRepository.save(phoneToBeUpdated);
    }

    @Override
    public Phone readById(Integer id) {
        log.info("Получен телефон с id = {}", id);
        return phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
    }

    @Override
    public List<Order> findAllOrders() {
        log.info("Просмотр всех заказов");
        return orderRepository.findByOrderByDateDesc();
    }

    @Override
    public List<Order> findWithPagination(int page, int size) {
            return orderRepository.findAll(PageRequest.of(page, size, Sort.by("date").descending())).getContent();
    }

}
