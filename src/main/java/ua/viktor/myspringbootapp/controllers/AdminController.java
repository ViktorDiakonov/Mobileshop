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

    // admin page
    @GetMapping("/admin_page")
    public String adminPage() {
        return "admin/admin-page";
    }

    // delete phone by id
    @DeleteMapping("/delete_phone/{id}")
    public String deletePhone(@PathVariable("id") int id) {
        adminService.deletePhoneById(id);
        return "redirect:/mobileshop/admin_page";
    }

    // delete order by id
    @DeleteMapping("/delete/order/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        adminService.deleteOrderById(id);
        return "redirect:/mobileshop/admin_page";
    }

    // search phone by id
    @GetMapping("/admin_page/search_phone")
    public String searchPhone(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("phone", phoneService.findPhone(id));
        return "admin/admin-search-phone";
    }

    // search order by id
    @GetMapping("/admin_page/search_order")
    public String searchOrder(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("order", adminService.findOrder(id));
        return "admin/admin-search-order";
    }

    // see all orders
    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        model.addAttribute("order", adminService.findAllOrders());
        return "admin/orders";
    }

    // see all orders with pagination
    @GetMapping("/orders/p")
    public String viewAllOrdersWithPagination(Model model, @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(value = "size") int size) {
        model.addAttribute("order", adminService.findWithPagination(page, size));
        return "admin/orders";
    }

    // see specific order
    @GetMapping("/{id}/order")
    public String viewOrderById(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", adminService.findOrder(id));
        return "admin/show-order";
    }

    //---------------------------------------------------------------
    // add new phone
    @GetMapping("/new_phone")
    public String addNewPhone(@ModelAttribute("phone") Phone phone) {
        return "admin/new-phone";
    }

    // save new phone
    @PostMapping()
    public String createNewPhone(@ModelAttribute("phone") @Valid Phone phone, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "admin/new-phone";
        adminService.create(phone);
        return "admin/view-created-phone";
    }
    //---------------------------------------------------------------

    //---------------------------------------------------------------
    // phone edit page
    @GetMapping("/{id}/edit_phone")
    public String editPhone(Model model, @PathVariable("id") int id) {
        model.addAttribute("phone", adminService.readById(id));
        return "admin/edit";
    }

    // save edit phone
    @PatchMapping("/{id}")
    public String updatePhone(@ModelAttribute("phone") @Valid Phone phone, BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "admin/edit";
        adminService.updateById(id, phone);
        return "redirect:/mobileshop/admin_page";
    }
    //--------------------------------------------------------------------
}
