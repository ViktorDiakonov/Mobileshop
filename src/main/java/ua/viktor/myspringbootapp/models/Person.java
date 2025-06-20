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

    @Size(min = 2, max = 20, message = "Від 2 до 20 букв")
    @Column(name = "name")
    private String userName;

    @Size(min = 4, message = "Від 4 до 10 символів")
    @Column(name = "password")
    private String password;

    @Size(min = 12, max = 12, message = "Має бути 12 цифр")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    private String role;
}