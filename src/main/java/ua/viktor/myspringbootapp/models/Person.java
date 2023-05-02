package ua.viktor.myspringbootapp.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Diakonov Viktor
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 20, message = "Iм'я має бути від 2 до 15 лiтер")
    @Column(name = "name")
    private String userName;

    // не нужно при шифровании @Size(min = 4, max = 10, message = "Пароль має бути від 4 до 10 символів")
    @Column(name = "password")
    private String password;

    @Size(min = 12, max = 12, message = "Номер телефону має бути довжиною 12 цифр і починатися з 380")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    private String role;

    // -------------------------костыль, переделать----------------------------------
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "person_id")
//    private List<Phone>phones = new ArrayList<>();
}