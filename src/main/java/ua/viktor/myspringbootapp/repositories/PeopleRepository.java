package ua.viktor.myspringbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viktor.myspringbootapp.models.Person;

import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByUserName(String name);

    Optional<Person> findByPhoneNumber(String phoneNumber); // Теперь поиск по телефону
}