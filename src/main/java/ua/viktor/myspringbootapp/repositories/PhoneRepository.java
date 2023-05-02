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

    @Query(value = "select * from phones where brand = 'Apple' ORDER BY model asc", nativeQuery = true)
    List<Phone> findAllPhonesApple(String brand);

    @Query(value = "select * from phones where brand = 'Xiaomi' ORDER BY model asc", nativeQuery = true)
    List<Phone> findAllPhonesXiaomi(String brand);

    @Query(value = "select * from phones where brand = 'Samsung' ORDER BY model asc", nativeQuery = true)
    List<Phone> findAllPhonesSamsung(String brand);
}