package ua.viktor.myspringbootapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.AdminService;
import ua.viktor.myspringbootapp.services.PhoneService;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhoneService phoneService;

    @Mock
    private AdminService adminService;

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void adminPage_ShouldReturnAdminPage() throws Exception {
        mockMvc.perform(get("/mobileshop/admin_page"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-page"));
    }

    @Test
    void deletePhone_ShouldRedirectToAdminPage() throws Exception {
        Phone phone = new Phone();
        phone.setImagePath("/uploads/image.jpg");
        when(phoneRepository.findById(anyInt())).thenReturn(Optional.of(phone));

        mockMvc.perform(delete("/mobileshop/delete_phone/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mobileshop/admin_page"));

        verify(adminService, times(1)).deletePhoneById(1);
    }

    @Test
    void searchPhone_ShouldReturnSearchPhoneView() throws Exception {
        when(phoneService.findPhoneById(anyInt())).thenReturn(new Phone());

        mockMvc.perform(get("/mobileshop/admin_page/search_phone").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-search-phone"));
    }

    @Test
    void searchOrder_ShouldReturnSearchOrderView() throws Exception {
        when(adminService.findOrder(anyInt())).thenReturn(null);

        mockMvc.perform(get("/mobileshop/admin_page/search_order").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-search-order"));
    }

    @Test
    void viewAllOrders_ShouldReturnOrdersView() throws Exception {
        mockMvc.perform(get("/mobileshop/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/orders"));
    }

    @Test
    void viewAllOrdersWithPagination_ShouldReturnOrdersView() throws Exception {
        mockMvc.perform(get("/mobileshop/orders/p").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/orders"));
    }

    @Test
    void viewOrderById_ShouldReturnSearchOrderView() throws Exception {
        when(adminService.findOrder(anyInt())).thenReturn(null);

        mockMvc.perform(get("/mobileshop/1/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-search-order"));
    }
}
