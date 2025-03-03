package ua.viktor.myspringbootapp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Size(min = 2, max = 20, message = "Iм'я має бути від 2 до 15 літер")
    @Column(name = "name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Size(min = 12, max = 12, message = "Номер телефону має бути довжиною 12 цифр і починатися з 380")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    private String role;
}