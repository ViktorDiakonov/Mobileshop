package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.AdminService;
import ua.viktor.myspringbootapp.services.PhoneService;

import javax.validation.Valid;
/**
 * @author Diakonov Viktor
 */
@Controller
@AllArgsConstructor
@RequestMapping("/mobileshop")
public class AdminController {

    private final PhoneService phoneService;
    private final AdminService adminService;

    // страница для админа
    @GetMapping("/admin_page")
    public String adminPage() {
        return "admin/admin-page";
    }

    // удаление телефона по id
    @DeleteMapping("/delete_phone/{id}")
    public String deletePhone(@PathVariable("id") int id) {
        adminService.deletePhoneById(id);
        return "redirect:/mobileshop/admin_page";
    }

    // удаление заказа по id
    @DeleteMapping("/delete/order/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        adminService.deleteOrderById(id);
        return "redirect:/mobileshop/admin_page";
    }

    // поиск телефона по id
    @GetMapping("/admin_page/search_phone")
    public String searchPhone(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("phone", phoneService.findPhone(id));
        return "admin/admin-search-phone";
    }

    // поиск заказа по id
    @GetMapping("/admin_page/search_order")
    public String searchOrder(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("order", adminService.findOrder(id));
        return "admin/admin-search-order";
    }

    // просмотреть все заказы
    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        model.addAttribute("order", adminService.findAllOrders());
        return "admin/orders";
    }

    // просмотреть все заказы с пагинацией
    @GetMapping("/orders/p")
    public String viewAllOrdersWithPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(value = "size") int size) {
        model.addAttribute("order", adminService.findWithPagination(page, size));
        return "admin/orders";
    }

    // отображает конкретный заказ
    @GetMapping("/{id}/order")
    public String viewOrderById(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", adminService.findOrder(id));
        return "admin/show-order";
    }

    //---------------------------------------------------------------
    // добавление нового телефона
    @GetMapping("/new_phone")
    public String addNewPhone(@ModelAttribute("phone") Phone phone) {
        return "admin/new-phone";
    }

    // сохранение нового телефона
    @PostMapping()
    public String createNewPhone(@ModelAttribute("phone") @Valid Phone phone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "admin/new-phone";
        adminService.create(phone);
        return "admin/view-created-phone";
    }
    //---------------------------------------------------------------

    //---------------------------------------------------------------
    // страница редактирования телефона
    @GetMapping("/{id}/edit_phone")
    public String editPhone(Model model, @PathVariable("id") int id) {
        model.addAttribute("phone", adminService.readById(id));
        return "admin/edit";
    }

    // сохранение отредактированного телефона
    @PatchMapping("/{id}")
    public String updatePhone(@ModelAttribute("phone") @Valid Phone phone, BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "admin/edit";
        adminService.updateById(id, phone);
        return "redirect:/mobileshop/admin_page";
    }
    //--------------------------------------------------------------------
}
