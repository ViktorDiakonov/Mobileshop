package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.repositories.PeopleRepository;
import ua.viktor.myspringbootapp.util.config.security.PersonDetails;

import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Service
@AllArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        Optional<Person> person = peopleRepository.findByUserName(userName);
//
//        if (person.isEmpty()) throw new UsernameNotFoundException("User not found!");
//        return new PersonDetails(person.get());
//    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByPhoneNumber(phoneNumber);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с номером " + phoneNumber + " не найден");
        }

        return new PersonDetails(person.get()); // Используем PersonDetails
    }
}