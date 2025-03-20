package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.repositories.PeopleRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@AllArgsConstructor
@Service
public class AuthServiceBean implements AuthService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<Person> show(String userName) {
        log.info("Для валидации - получен Person с именем = {}", userName);
        return peopleRepository.findByUserName(userName);
    }

    @Transactional
    @Override
    public void register(Person person) {
        log.info("Регистрация нового пользователя: {}", person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
        log.info("Пользователь {} успешно зарегистрирован", person.getUserName());
    }
}