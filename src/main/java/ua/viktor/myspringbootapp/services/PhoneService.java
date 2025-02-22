package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import java.util.List;
/**
 * @author Diakonov Viktor
 */
public interface PhoneService {

//    // отображение телефонов apple
//    List<Phone> readAllPhonesByBrandApple(String phone);
//
//    // отображение телефонов xiaomi
//    List<Phone> readAllPhonesByBrandXiaomi(String phone);
//
//    // отображение телефонов samsung
//    List<Phone> readAllPhonesByBrandSamsung(String phone);

    // отображение одного телефона на странице
    Phone findPhone(int id);

    // посылает параметры заказа из web формы и сохраняет его в БД
    void createOrder(Order order);

    // todo тест для личного кабинета пользователя
    List<Order> readAllOrdersByPersonPhone(String phone);

    List<Phone> findAllPhones();

    List<Phone> findLast16Phones() ;

    List<Phone> readPhonesByBrandSorted(String brand, String sort);

}
