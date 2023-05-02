package ua.viktor.myspringbootapp.util.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
/**
 * @author Diakonov Viktor
 */
@AllArgsConstructor
@Getter
public class OrderErrorResponse {
    private String message;
    private Date timestamp;
}
