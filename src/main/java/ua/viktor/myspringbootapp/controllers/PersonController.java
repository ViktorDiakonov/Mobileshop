package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.services.AdminService;
import ua.viktor.myspringbootapp.services.PhoneService;
import javax.validation.Valid;
/**
 * @author Diakonov Viktor
 */
@Controller
@AllArgsConstructor
@RequestMapping("/mobileshop")
public class PersonController {

    private final PhoneService phoneService;
    private final AdminService adminService;

    // главная страница
    @GetMapping("/")
    public String mainPage() {
        return "person/main-page";
    }

    // отображение телефонов apple
    @GetMapping("/phones/apple")
    public String phonesApple(Model model) {
        model.addAttribute("phone", phoneService.readAllPhonesByBrandApple("apple"));
        return "person/list-models";
    }

    // отображение телефонов samsung
    @GetMapping("/phones/samsung")
    public String phonesSamsung(Model model) {
        model.addAttribute("phone", phoneService.readAllPhonesByBrandSamsung("samsung"));
        return "person/list-models";
    }

    // отображение телефонов xiaomi
    @GetMapping("/phones/xiaomi")
    public String phonesXiaomi(Model model) {
        model.addAttribute("phone", phoneService.readAllPhonesByBrandXiaomi("xiaomi"));
        return "person/list-models";
    }

    // отображение конкретного телефона
    @GetMapping("/phone/{id}")
    public String viewPhoneById(@PathVariable("id") int id, Model model) {
        model.addAttribute("phone", phoneService.findPhone(id));
        return "person/show-phone";
    }

//----------------------------------------------------------------------
    // страница создания заказа
    @GetMapping("/{id}/new_order")
    public String createNewOrder(@PathVariable("id") int id, Model model) {
        model.addAttribute("phone", adminService.readById(id));
        return "person/order";
    }

    // сохранение нового заказа
    @PostMapping("/{id}/order")
    public String createOrder(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/mobileshop/{id}/new_order";
        phoneService.createOrder(order);
        return "person/buy";
    }
//---------------------------------------------------------------------

    // возвращает страничку "О нас"
    @GetMapping("/aboutus")
    public String aboutUs(){
        return "person/aboutus";
    }

    // возвращает страничку "Контакты"
    @GetMapping("/contacts")
    public String contacts(){
        return "person/contacts";
    }

    // возвращает страничку "Условия доставки"
    @GetMapping("/delivery")
    public String delivery(){
        return "person/delivery";
    }

    // todo тест - нужен "RestController"
//    @GetMapping(value = "/my_orders", params = {"person_phone"})
//    public List<Order> myOrders(@RequestParam(value = "person_phone") String person_phone) {
//        return phoneService.readAllOrdersByPersonPhone(person_phone);
//    }
//
    // todo тест для личного кабинета
    @GetMapping("/my_orders2")
    public String myOrders2(Model model) {
        model.addAttribute("order", phoneService.readAllOrdersByPersonPhone("38090000000"));
        return "person/list-orders";
    }

    @GetMapping("/my_orders")
    public String viewAllOrders(Model model) {
        model.addAttribute("order", adminService.findAllOrders());
        return "person/list-orders";
    }

}




