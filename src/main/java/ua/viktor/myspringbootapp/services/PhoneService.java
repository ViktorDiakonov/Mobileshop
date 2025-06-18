package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.models.Phone;

import java.util.List;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
public interface PhoneService {

    Phone findPhoneById(int id);

    void createOrder(Order order);

    List<Phone> findLast20Phones();

    List<Phone> readPhonesByBrandSorted(String brand, String sort);

    Optional<Person> personPhoneNumber(String phoneNumber);
}