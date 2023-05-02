package ua.viktor.myspringbootapp.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.services.AuthService;
import ua.viktor.myspringbootapp.services.PhoneService;
/**
 * @author Diakonov Viktor
 */
@Component
@AllArgsConstructor
public class PersonValidator implements Validator {

    private final AuthService authService;

    // настраиваем, какой класс будет валидироваться
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (authService.show(person.getUserName()).isPresent()) {
            errors.rejectValue("userName", "", "Це ім'я вже зайняте");
        }
    }
}
