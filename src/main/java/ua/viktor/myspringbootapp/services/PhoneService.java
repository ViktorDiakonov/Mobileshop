package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import java.util.List;
/**
 * @author Diakonov Viktor
 */
public interface PhoneService {

    // отображение одного телефона на странице
    Phone findPhone(int id);

    // посылает параметры заказа из web формы и сохраняет его в БД
    void createOrder(Order order);

    List<Phone> findLast16Phones() ;

    List<Phone> readPhonesByBrandSorted(String brand, String sort);

    List<String> getAllBrands();

}
