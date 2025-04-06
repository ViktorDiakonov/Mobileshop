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

    // Поиск по точному совпадению модели (регистрозависимый)
    List<Phone> findByModel(String model);

    // Поиск по частичному совпадению модели (без учета регистра)
    List<Phone> findByModelContainingIgnoreCase(String modelPart);

    // Альтернативный вариант с аннотацией @Query
    @Query("SELECT p FROM Phone p WHERE LOWER(p.model) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Phone> searchByModel(@Param("query") String query);

    Page<Phone> findByModelContainingIgnoreCase(String modelPart, Pageable pageable);

    List<Phone> findByModelContainingIgnoreCase(String modelPart, Sort sort);
}