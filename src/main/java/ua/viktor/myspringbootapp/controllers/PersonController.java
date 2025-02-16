package ua.viktor.myspringbootapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.services.AdminService;
import ua.viktor.myspringbootapp.services.PhoneService;
import ua.viktor.myspringbootapp.services.PhoneServiceBean;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * @author Diakonov Viktor
 */
@Controller
@AllArgsConstructor
@RequestMapping("/mobileshop")
public class PersonController {

    private final PhoneService phoneService;
    private final AdminService adminService;
    private final PhoneServiceBean phoneServiceBean;

    //    // main page
//    @GetMapping("/")
//    public String mainPage() {
//        return "person/main-page";
//    }
// main page with all phones
//    @GetMapping("/")
//    public String mainPage(Model model) {
//        model.addAttribute("phone", phoneService.findAllPhones());
//        return "person/main-page";
//    }
    @GetMapping("/")
    public String mainPage(Model model) {
        List<Phone> phones = phoneService.findAllPhones();
        Collections.shuffle(phones); // Перемешиваем список в случайном порядке
        model.addAttribute("phone", phones);
        return "person/main-page";
    }

    // show Apple phones
//    @GetMapping("/phones/apple")
//    public String phonesApple(Model model) {
//        model.addAttribute("phone", phoneService.readAllPhonesByBrandApple("apple"));
//        return "person/list-models";
//    }
//    @GetMapping("/phones/apple")
//    public String phonesApple(@RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
//                              Model model) {
//        model.addAttribute("phone", phoneServiceBean.readAllPhonesByBrandAppleSorted(sort));
//        return "person/list-models";
//    }
//
//
//    // show phones samsung
//    @GetMapping("/phones/samsung")
//    public String phonesSamsung(Model model) {
//        model.addAttribute("phone", phoneService.readAllPhonesByBrandSamsung("samsung"));
//        return "person/list-models";
//    }
//
//    // show phones xiaomi
//    @GetMapping("/phones/xiaomi")
//    public String phonesXiaomi(Model model) {
//        model.addAttribute("phone", phoneService.readAllPhonesByBrandXiaomi("xiaomi"));
//        return "person/list-models";
//    }

    @GetMapping("/phones/{brand}")
    public String getPhonesByBrand(@PathVariable String brand,
                                   @RequestParam(value = "sort", required = false, defaultValue = "desc") String sort,
                                   Model model) {
        model.addAttribute("phone", phoneServiceBean.readPhonesByBrandSorted(brand, sort));
        return "person/list-models";
    }

    // show specific phone
    @GetMapping("/phone/{id}")
    public String viewPhoneById(@PathVariable("id") int id, Model model) {
        model.addAttribute("phone", phoneService.findPhone(id));
        return "person/show-phone";
    }

    //----------------------------------------------------------------------
    // order creation page
    @GetMapping("/{id}/new_order")
    public String createNewOrder(@PathVariable("id") int id, Model model) {
        model.addAttribute("phone", adminService.readById(id));
        return "person/order";
    }

    @PostMapping("/{id}/order")
    public String createOrder(@PathVariable int id, @ModelAttribute("order") @Valid Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/mobileshop/" + id + "/new_order";
        }

        // Получаем данные о телефоне по его id
        Phone phone = phoneService.findPhone(id);
        if (phone == null) {
            return "redirect:/mobileshop"; // Перенаправляем на главную, если телефона нет
        }

        // Устанавливаем данные телефона в заказ
        order.setBrand(phone.getBrand());
        order.setModel(phone.getModel());
        order.setMemorySize(phone.getMemorySize());
        order.setPrice(phone.getPrice());
        order.setImagePath(phone.getImagePath()); // Заполняем путь к изображению

        // Сохраняем заказ
        phoneService.createOrder(order);

        // Передаем заказ в модель для отображения на странице "buy"
        model.addAttribute("order", order);

        return "person/buy";
    }

//---------------------------------------------------------------------

    // returns the page "About us"
    @GetMapping("/aboutus")
    public String aboutUs() {
        return "person/aboutus";
    }

    // returns the page "Contacts"
    @GetMapping("/contacts")
    public String contacts() {
        return "person/contacts";
    }

    // returns the page "Conditions of delivery"
    @GetMapping("/delivery")
    public String delivery() {
        return "person/delivery";
    }

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




