package ua.viktor.myspringbootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.viktor.myspringbootapp.models.Phone;

import java.util.List;

/**
 * @author Diakonov Viktor
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    List<Phone> findByBrandOrderByModelAsc(String brand);

    List<Phone> findByBrandOrderByPriceAsc(String brand);

    List<Phone> findByBrandOrderByPriceDesc(String brand);

//    @Query(value = "SELECT * FROM phones ORDER BY id DESC LIMIT 16", nativeQuery = true)
//    List<Phone> findLast16Phones();

    List<Phone> findTop16ByOrderByIdDesc();


}