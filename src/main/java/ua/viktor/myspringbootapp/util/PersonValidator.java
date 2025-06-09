package ua.viktor.myspringbootapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.services.AuthService;
/**
 * @author Diakonov Viktor
 */
//@Component
//@RequiredArgsConstructor
//public class PersonValidator implements Validator {
//
//    private final AuthService authService;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Person.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        if (!(target instanceof Person)) {
//            return; // Защита от неправильного типа данных
//        }
//
//        Person person = (Person) target;
//
//        authService.show(person.getUserName()).ifPresent(existingPerson ->
//                errors.rejectValue("userName", "", "Це ім'я вже зайнято")
//
//        );
//    }
//}
@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final AuthService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof Person)) {
            return;
        }

        Person person = (Person) target;

        authService.findByPhoneNumber(person.getPhoneNumber()).ifPresent(existingPerson ->
                errors.rejectValue("phoneNumber", "", "Цей номер вже зареєстрований")
        );
    }
}
