//package ua.viktor.myspringbootapp.util.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.Arrays;
//import java.util.Date;
///**
// * @author Diakonov Viktor
// */
//@ControllerAdvice
//public class OrderExceptionHandler {
//
//    @ExceptionHandler
//    private ResponseEntity<OrderErrorResponse> handleException(OrderNotFoundException e, WebRequest request) {
//        OrderErrorResponse response = new OrderErrorResponse(
//                "Не знайдено замовлення з номером = " + Arrays.toString(request.getParameterValues("id"))
//                , new Date());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//}
