package ua.viktor.myspringbootapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.OrderRepository;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.util.exception.PhoneNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceBeanTest {

    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private OrderRepository orderRepository;
    private PhoneServiceBean phoneService;

    @BeforeEach
    public void setUp() {
        phoneRepository = mock(PhoneRepository.class);
        orderRepository = mock(OrderRepository.class);
        phoneService = new PhoneServiceBean(phoneRepository, orderRepository);
    }

    @Test
    public void testFindPhoneByIdThrowsException() {
        // Настройка поведения мока
        when(phoneRepository.findById(1)).thenReturn(Optional.empty());

        // Проверка, что метод выбрасывает исключение
        assertThrows(PhoneNotFoundException.class, () -> phoneService.findPhoneById(1));

        // Убеждаемся, что репозиторий был вызван
        verify(phoneRepository).findById(1);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();

        phoneService.createOrder(order);

        verify(orderRepository).save(order);
    }

    @Test
    public void testFindLast16Phones() {
        when(phoneRepository.findTop20ByOrderByIdDesc()).thenReturn(Collections.emptyList());
        List<Phone> phones = phoneService.findLast20Phones();
        assertNotNull(phones);
        assertEquals(0, phones.size());
        verify(phoneRepository).findTop20ByOrderByIdDesc();
    }

    @Test
    public void testReadPhonesByBrandSortedAsc() {
        // Данные для мока
        List<Phone> phones = List.of(new Phone(), new Phone());

        // Настройка мока
        when(phoneRepository.findByBrandOrderByPriceAsc("Samsung")).thenReturn(phones);

        // Вызов метода
        List<Phone> result = phoneService.readPhonesByBrandSorted("Samsung", "desc");

        // Проверка
        assertEquals(2, result.size());
        verify(phoneRepository).findByBrandOrderByPriceAsc("Samsung");
    }

    @Test
    public void testReadPhonesByBrandSortedDesc() {
        // Данные для мока
        List<Phone> phones = List.of(new Phone(), new Phone());

        // Настройка мока
        when(phoneRepository.findByBrandOrderByPriceDesc("Samsung")).thenReturn(phones);

        // Вызов метода с неформатированным брендом
        List<Phone> result = phoneService.readPhonesByBrandSorted("Samsung", "asc");

        // Проверка
        assertEquals(2, result.size());
        verify(phoneRepository).findByBrandOrderByPriceDesc("Samsung");
    }

//    @Test
//    public void testGetAllBrands() {
//        Phone phone1 = new Phone();
//        phone1.setBrand("Samsung");
//        Phone phone2 = new Phone();
//        phone2.setBrand("Apple");
//        when(phoneRepository.findAll()).thenReturn(Arrays.asList(phone1, phone2));
//        List<String> brands = phoneService.getAllBrands();
//        assertEquals(2, brands.size());
//        assertTrue(brands.contains("Samsung"));
//        assertTrue(brands.contains("Apple"));
//        verify(phoneRepository).findAll();
//    }
}
