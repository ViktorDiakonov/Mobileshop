package ua.viktor.myspringbootapp.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mobileshop/api/phones")
public class PhoneController {

    private final PhoneRepository phoneRepository;

    public PhoneController(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<Phone> phones = phoneRepository.findByModelContainingIgnoreCase(q);
        model.addAttribute("phones", phones);
        model.addAttribute("query", q);
        return "search-results"; // имя вашего Thymeleaf шаблона
    }

    // Поиск с пагинацией
    @GetMapping("/search-page")
    public Page<Phone> searchPhonesPaginated(
            @RequestParam String model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return phoneRepository.findByModelContainingIgnoreCase(model, pageable);
    }

    @GetMapping("/search-sorted")
    public List<Phone> searchPhonesSorted(
            @RequestParam String model,
            @RequestParam(defaultValue = "price,asc") String[] sort) {

        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sort[0]);

        return phoneRepository.findByModelContainingIgnoreCase(model, Sort.by(order));
    }
}