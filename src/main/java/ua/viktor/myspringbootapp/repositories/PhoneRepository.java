package ua.viktor.myspringbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.viktor.myspringbootapp.models.Phone;

import java.util.List;

/**
 * @author Diakonov Viktor
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    List<Phone> findByBrandOrderByPriceAsc(String brand);

    List<Phone> findByBrandOrderByPriceDesc(String brand);

    List<Phone> findTop16ByOrderByIdDesc();


}