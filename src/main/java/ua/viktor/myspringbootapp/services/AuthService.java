package ua.viktor.myspringbootapp.services;

import ua.viktor.myspringbootapp.models.Person;

import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
public interface AuthService {

    // для валидации по имени
    Optional<Person> show(String userName);

    // регистрирует нового пользователя и присваивает ему роль USER
    void register(Person person);

    Optional<Person> findByPhoneNumber(String phoneNumber);

}