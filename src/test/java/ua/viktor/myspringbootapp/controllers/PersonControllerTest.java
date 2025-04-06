package ua.viktor.myspringbootapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.PhoneService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonControllerTest {

    @Mock
    private PhoneService phoneService;

    @Mock
    private Model model;

    private PersonController personController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personController = new PersonController(phoneService);
    }

    @Test
    public void testMainPage() {
        when(phoneService.findLast20Phones()).thenReturn(List.of(new Phone(), new Phone()));

        String viewName = personController.mainPage(model);

        verify(phoneService).findLast20Phones();
        verify(model).addAttribute(eq("phone"), any());
        assertEquals("person/main-page", viewName);
    }

    @Test
    public void testGetPhonesByBrand() {
        when(phoneService.readPhonesByBrandSorted("Samsung", "desc")).thenReturn(List.of(new Phone()));
//        when(phoneService.getAllBrands()).thenReturn(List.of("Samsung", "Apple"));

        String viewName = personController.getPhonesByBrand("Samsung", "desc", model);

        verify(phoneService).readPhonesByBrandSorted("Samsung", "desc");
//        verify(phoneService).getAllBrands();
        verify(model).addAttribute(eq("phone"), any());
        verify(model).addAttribute(eq("selectedBrand"), any());
        verify(model).addAttribute(eq("brands"), any());
        assertEquals("person/list-models", viewName);
    }

    @Test
    public void testViewPhoneById() {
        Phone phone = new Phone();
        when(phoneService.findPhoneById(1)).thenReturn(phone);

        String viewName = personController.viewPhoneById(1, model);

        verify(phoneService).findPhoneById(1);
        verify(model).addAttribute("phone", phone);
        assertEquals("person/show-phone", viewName);
    }

    @Test
    public void testCreateNewOrder() {
        Phone phone = new Phone();
        when(phoneService.findPhoneById(1)).thenReturn(phone);

        String viewName = personController.createNewOrder(1, model);

        verify(phoneService).findPhoneById(1);
        verify(model).addAttribute("phone", phone);
        assertEquals("person/order", viewName);
    }

    @Test
    public void testCreateOrder() {
        Phone phone = new Phone();
        phone.setBrand("Samsung");
        phone.setModel("Galaxy S21");
        phone.setPrice(1000);

        when(phoneService.findPhoneById(1)).thenReturn(phone);

        Order order = new Order();
        BindingResult bindingResult = mock(BindingResult.class);
        String viewName = personController.createOrder(1, 2, order, bindingResult, model);

        verify(phoneService).findPhoneById(1);
        verify(phoneService).createOrder(any(Order.class));
        verify(model).addAttribute("order", order);
        assertEquals("person/buy", viewName);
    }
}