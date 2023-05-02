package ua.viktor.myspringbootapp.util.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Date;
/**
 * @author Diakonov Viktor
 */
@ControllerAdvice
public class PhoneExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<PhoneErrorResponse> handleException(PhoneNotFoundException e, WebRequest request) {
        PhoneErrorResponse response = new PhoneErrorResponse(
                "Не знайден телефон з кодом = " + Arrays.toString(request.getParameterValues("id"))
                , new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}