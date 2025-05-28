//package ua.viktor.myspringbootapp.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import ua.viktor.myspringbootapp.models.Person;
//import ua.viktor.myspringbootapp.repositories.PeopleRepository;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthServiceBeanTest {
//
//    @Mock
//    private PeopleRepository peopleRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    private AuthServiceBean authService;
//
//    @BeforeEach
//    public void setUp() {
//        authService = new AuthServiceBean(peopleRepository, passwordEncoder);
//    }
//
//    @Test
//    public void testShowReturnsPerson() {
//        Person person = new Person();
//        person.setUserName("testUser");
//        when(peopleRepository.findByUserName("testUser")).thenReturn(Optional.of(person));
//
//        Optional<Person> result = authService.show("testUser");
//
//        assertTrue(result.isPresent());
//        assertEquals("testUser", result.get().getUserName());
//        verify(peopleRepository).findByUserName("testUser");
//    }
//
//    @Test
//    public void testShowReturnsEmpty() {
//        when(peopleRepository.findByUserName("unknownUser")).thenReturn(Optional.empty());
//
//        Optional<Person> result = authService.show("unknownUser");
//
//        assertFalse(result.isPresent());
//        verify(peopleRepository).findByUserName("unknownUser");
//    }
//
//    @Test
//    public void testRegisterEncodesPasswordAndSavesPerson() {
//        Person person = new Person();
//        person.setUserName("newUser");
//        person.setPassword("rawPassword");
//        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
//
//        authService.register(person);
//
//        assertEquals("encodedPassword", person.getPassword());
//        assertEquals("ROLE_USER", person.getRole());
//        verify(passwordEncoder).encode("rawPassword");
//        verify(peopleRepository).save(person);
//    }
//}
