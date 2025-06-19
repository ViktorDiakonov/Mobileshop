package ua.viktor.myspringbootapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.viktor.myspringbootapp.models.Order;

@Component
@RequiredArgsConstructor
public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;

        // Проверка формата телефона (380XXXXXXXXX)
//        if (order.getPersonPhone() != null && !order.getPersonPhone().matches("380\\d{9}")) {
//            errors.rejectValue("personPhone", "invalid.phone", "Номер має починатись з 380 та містити 12 цифр");
//        }

        // Проверка количества товара
//        if (order.getQuantity() == null || order.getQuantity() <= 0) {
//            errors.rejectValue("quantity", "invalid.quantity", "Кількість має бути більше 0");
//        }
    }
}