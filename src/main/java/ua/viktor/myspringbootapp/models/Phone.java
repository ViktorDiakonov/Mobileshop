package ua.viktor.myspringbootapp.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
/**
 * @author Diakonov Viktor
 */
@Entity
@Table(name = "phones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Phone {

    private String imagePath; // Путь к файлу изображения

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

    @Positive(message = "ціна має бути більшою за нуль")
    @NotNull(message = "ціна не може бути порожньою")
    @Column(name = "price")
    private Integer price;

    // todo ------------------костыль, возможно нужно переделать--------------------------
    @Size(min = 2, max = 20, message = "Ім'я має бути від 2 до 20 букв")
    @Column(name = "person_name")
    private String personName;

    @Size(min = 12, max = 12, message = "Номер телефону має бути довжиною 12 цифр і починатися з 380")
    @Column(name = "person_phone")
    private String personPhone;
}