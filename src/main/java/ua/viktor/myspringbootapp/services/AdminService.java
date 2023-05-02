package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;

import java.util.List;
/**
 * @author Diakonov Viktor
 */
public interface AdminService {

    // удаление телефона по id
    void deletePhoneById(Integer id);

    // удаление заказа по id
    void deleteOrderById(Integer id);

    // отображает конкретный заказ
    Order findOrder(int id);

    // посылает параметры телефона из web формы и сохраняет его в БД
    Phone create(Phone phone);

    // сохранение обновленного телефона
    Phone updateById(Integer id, Phone phone);

    // страница создания заказа
    Phone readById(Integer id);

    // показать админу все заказы
    List<Order> findAllOrders();

    // показать админу все заказы с пагинацией
    List<Order> findWithPagination(int page, int size);
}
