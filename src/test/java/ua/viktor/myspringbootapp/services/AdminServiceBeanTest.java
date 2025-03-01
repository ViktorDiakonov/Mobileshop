package ua.viktor.myspringbootapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.OrderRepository;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.util.exception.OrderNotFoundException;
import ua.viktor.myspringbootapp.util.exception.PhoneNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AdminServiceBeanTest {

    @InjectMocks
    private AdminServiceBean adminServiceBean; // создается реальный объект с замоканными репозиториями

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private OrderRepository orderRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPhone() {
        Phone phone = new Phone();
        phone.setBrand("Samsung");
        phone.setModel("Galaxy S22");

        adminServiceBean.create(phone);

        verify(phoneRepository, times(1)).save(phone);
    }

    @Test
    void deletePhoneById_Success() {
        Phone phone = new Phone();
        phone.setId(1);
        when(phoneRepository.findById(1)).thenReturn(Optional.of(phone));

        adminServiceBean.deletePhoneById(1);

        verify(phoneRepository, times(1)).delete(phone);
    }

    @Test
    void deletePhoneById_ThrowsException() {
        when(phoneRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PhoneNotFoundException.class, () -> adminServiceBean.deletePhoneById(1));
    }

    @Test
    void findOrder_Success() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order foundOrder = adminServiceBean.findOrder(1);

        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getId());
    }

    @Test
    void findOrder_ThrowsException() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> adminServiceBean.findOrder(1));
    }


//    @Test
//    void testUpdatePhoneWithNewImage() throws IOException {
//        // Подготовка данных
//        Phone phone = new Phone();
//        phone.setId(1);
//        phone.setImagePath("/uploads/old_image.png");
//
//        // Мок нового файла
//        MultipartFile newFile = mock(MultipartFile.class);
//        when(newFile.isEmpty()).thenReturn(false);
//        when(newFile.getOriginalFilename()).thenReturn("new_image.png");
//
//        // Мок репозитория
//        when(phoneRepository.findById(1)).thenReturn(Optional.of(phone));
//
//        // Мок методов deleteOldFile и saveFile
//        doNothing().when(adminServiceBean).deleteOldFile("/uploads/old_image.png");
//        when(adminServiceBean.saveFile(newFile)).thenReturn("/uploads/new_image.png");
//
//        // Вызов метода
//        adminServiceBean.updateById(1, phone, newFile);
//
//        // Проверки
//        verify(phoneRepository, times(1)).save(phone); // Проверка, что телефон сохранен
//        verify(adminServiceBean, times(1)).deleteOldFile("/uploads/old_image.png"); // Проверка удаления старого файла
//        verify(adminServiceBean, times(1)).saveFile(newFile); // Проверка сохранения нового файла
//
//        // Проверка, что imagePath обновлен
//        assertEquals("/uploads/new_image.png", phone.getImagePath());
//    }



    @Test
    void findWithPagination() {
        // Мок страницы с двумя заказами
        Page<Order> mockPage = new PageImpl<>(List.of(new Order(), new Order()));
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(mockPage);

        // Вызов метода с page = 0 и size = 2
        List<Order> orders = adminServiceBean.findWithPagination(0, 2);

        // Проверка вызова с правильными параметрами
        verify(orderRepository, times(1)).findAll(PageRequest.of(0, 2, Sort.by("date").descending()));

        // Убедимся, что вообще было взаимодействие с моком
        verify(orderRepository, atLeastOnce()).findAll(any(PageRequest.class));

        // Проверка размера результата
        assertEquals(2, orders.size());
    }

}
