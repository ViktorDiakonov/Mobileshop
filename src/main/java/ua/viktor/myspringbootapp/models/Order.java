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

    @Size(min = 2, max = 15, message = "Бренд має бути від 2 до 15 літер")
    @Column(name = "brand")
    private String brand;

    @Size(min = 2, max = 25, message = "Модель має бути від 2 до 25 символів")
    @Column(name = "model")
    private String model;

    @Size(min = 1, max = 5, message = "Розмір пам'яті має бути від 1 до 5 цифр")
    @Column(name = "memory_size")
    private String memorySize;

    @Min(value = 0, message = "Ціна не повинна бути негативною")
    @Column(name = "price")
    private int price;

    @Size(max = 150, message = "Максимальна довжина повідомлення 150 символів")
    @Column(name = "person_comment")
    private String personComment;

    @Size(min = 2, max = 20, message = "Не менше двох букв")
    @Column(name = "person_name")
    private String personName;

    @Size(min = 12, max = 12, message = "Загалом 12 цифр")
    @Column(name = "person_phone")
    private String personPhone;

    @Column(name = "point")
    private String point;

    private String imagePath;

    @Column(name = "quantity")
    private Integer quantity;

    private Date date = Date.from(Instant.now());
}