package ua.viktor.myspringbootapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<Phone> findTop20ByOrderByIdDesc();

    // Поиск по частичному совпадению модели (без учета регистра)
    List<Phone> findByModelContainingIgnoreCase(String modelPart);
}