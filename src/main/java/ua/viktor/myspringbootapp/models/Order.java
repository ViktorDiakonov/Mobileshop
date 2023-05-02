package ua.viktor.myspringbootapp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
/**
 * @author Diakonov Viktor
 */
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 15, message = "Бренд має бути від 2 до 15 лiтер")
    @Column(name = "brand")
    private String brand;

    @Size(min = 2, max = 15, message = "Модель має бути від 2 до 15 символів")
    @Column(name = "model")
    private String model;

    @Size(min = 1, max = 5, message = "Розмір пам'яті має бути від 1 до 5 цифр")
    @Column(name = "memory_size")
    private String memorySize;

    @Min(value = 0, message = "Ціна не повинна бути негативною")
    @Column(name = "price")
    private int price;

    @Size(min = 2, max = 20, message = "Ім'я має бути від 2 до 20 букв")
    @Column(name = "person_name")
    public String personName;

    @Size(min = 9, max = 14, message = "Номер телефону має бути довжиною вiд 9 до 14 цифр")
    @Column(name = "person_phone")
    private String personPhone;

    @Column(name = "point")
    private String point;

    private Date date = Date.from(Instant.now());
}
