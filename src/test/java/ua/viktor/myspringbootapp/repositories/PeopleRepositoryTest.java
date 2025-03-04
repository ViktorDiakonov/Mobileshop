package ua.viktor.myspringbootapp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.viktor.myspringbootapp.models.Person;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository peopleRepository;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setUserName("testUser");
        person.setPassword("password123");
        person.setPhoneNumber("380123456789");
        person.setRole("USER");
        peopleRepository.save(person);
    }

    @Test
    void findByUserName_ShouldReturnPerson_WhenUserExists() {
        Optional<Person> foundPerson = peopleRepository.findByUserName("testUser");

        assertThat(foundPerson).isPresent();
        assertThat(foundPerson.get().getUserName()).isEqualTo("testUser");
    }

    @Test
    void findByUserName_ShouldReturnEmpty_WhenUserDoesNotExist() {
        Optional<Person> foundPerson = peopleRepository.findByUserName("nonExistentUser");

        assertThat(foundPerson).isNotPresent();
    }

    @Test
    void save_ShouldPersistPerson() {
        Person newPerson = new Person();
        newPerson.setUserName("newUser");
        newPerson.setPassword("password123");
        newPerson.setPhoneNumber("380987654321");
        newPerson.setRole("USER");

        Person savedPerson = peopleRepository.save(newPerson);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isGreaterThan(0);
        assertThat(savedPerson.getUserName()).isEqualTo("newUser");
    }

    @Test
    void delete_ShouldRemovePerson() {
        peopleRepository.delete(person);

        Optional<Person> deletedPerson = peopleRepository.findByUserName("testUser");

        assertThat(deletedPerson).isNotPresent();
    }
}
