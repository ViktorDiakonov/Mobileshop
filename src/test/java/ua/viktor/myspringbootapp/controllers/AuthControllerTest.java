package ua.viktor.myspringbootapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.viktor.myspringbootapp.models.Person;
import ua.viktor.myspringbootapp.services.AuthService;
import ua.viktor.myspringbootapp.util.PersonValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private PersonValidator personValidator;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void registrationPage_ShouldReturnRegistrationView() throws Exception {
        mockMvc.perform(get("/mobileshop/auth/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/registration"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    void performRegistrationPage_WithValidPerson_ShouldRedirectToLogin() throws Exception {
        Person person = new Person();
        doNothing().when(authService).register(any(Person.class));


        mockMvc.perform(post("/mobileshop/auth/registration")
                        .flashAttr("person", person))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mobileshop/auth/login"));

        verify(authService, times(1)).register(any(Person.class));
    }

    @Test
    void performRegistrationPage_WithBindingErrors_ShouldReturnRegistrationView() throws Exception {
        Person person = new Person();
        person.setUserName(""); // не проходит валидацию, слишком короткое имя
        person.setPhoneNumber("123"); // неверный формат номера телефона

        mockMvc.perform(post("/mobileshop/auth/registration")
                        .flashAttr("person", person))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/registration"));

        verify(authService, never()).register(any(Person.class));
    }



    @Test
    void loginPage_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/mobileshop/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }
}
