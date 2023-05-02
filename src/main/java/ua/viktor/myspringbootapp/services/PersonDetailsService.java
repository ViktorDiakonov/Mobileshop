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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUserName(s);

        if (person.isEmpty()) throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(person.get());
    }
}
