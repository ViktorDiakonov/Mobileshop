package ua.viktor.myspringbootapp.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.viktor.myspringbootapp.models.Order;
import ua.viktor.myspringbootapp.models.Phone;
import ua.viktor.myspringbootapp.repositories.OrderRepository;
import ua.viktor.myspringbootapp.repositories.PhoneRepository;
import ua.viktor.myspringbootapp.util.exception.OrderNotFoundException;
import ua.viktor.myspringbootapp.util.exception.PhoneNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * @author Diakonov Viktor
 */
@Slf4j
@AllArgsConstructor
@Service
public class AdminServiceBean implements AdminService {

    private final OrderRepository orderRepository;
    private final PhoneRepository phoneRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads";

    @Override
    public void deletePhoneById(Integer id) {
        log.info("Попытка удаления телефона с id = {}", id);
        Phone phone = phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
        phoneRepository.delete(phone);
        log.info("Телефон с id = {} успешно удален", id);
    }

    @Override
    public void deleteOrderById(Integer id) {
        log.info("Попытка удаления заказа с id = {}", id);
        orderRepository.deleteById(id);
        log.info("Заказ с id = {} успешно удален", id);
    }

    @Override
    public Order findOrder(int id) {
        log.info("Поиск заказа с id = {}", id);
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public void create(Phone phone) {
        log.info("Попытка создания нового телефона: {}", phone);
        phoneRepository.save(phone);
    }

    //Update File + delete old image-------------------------------------------
    @Override
    public void updateById(Integer id, Phone updatedPhone, MultipartFile file) {
        log.info("Попытка обновления телефона с id = {}", id);
        Phone phoneToBeUpdated = readById(id);

        phoneToBeUpdated.setBrand(updatedPhone.getBrand());
        phoneToBeUpdated.setModel(updatedPhone.getModel());
        phoneToBeUpdated.setMemorySize(updatedPhone.getMemorySize());
        phoneToBeUpdated.setPrice(updatedPhone.getPrice());

        // Обновление фото, если был загружен новый файл
        if (file != null && !file.isEmpty()) {
            try {
                // Удаляем старый файл
                deleteOldFile(phoneToBeUpdated.getImagePath());

                // Сохраняем новый файл и обновляем путь к изображению
                String newImagePath = saveFile(file);
                if (newImagePath != null) {
                    phoneToBeUpdated.setImagePath(newImagePath);
                }
            } catch (IOException e) {
                log.error("Ошибка загрузки файла при обновлении телефона", e);
            }
        }

        phoneRepository.save(phoneToBeUpdated);
        log.info("Телефон с id = {} успешно обновлен", id);
    }

    public void deleteOldFile(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            String oldFilePath = System.getProperty("user.dir") + "/src/main/resources/static" + imagePath;
            File oldFile = new File(oldFilePath);
            if (oldFile.exists() && oldFile.delete()) {
                log.info("Удален старый файл изображения: {}", oldFilePath);
            } else {
                log.warn("Не удалось удалить файл: {}", oldFilePath);
            }
        }
    }
    //-------------------------------------------------------------------

    @Override
    public Phone readById(Integer id) {
        log.info("Попытка получения телефона с id = {}", id);
        return phoneRepository.findById(id).orElseThrow(PhoneNotFoundException::new);
    }

    @Override
    public List<Order> findAllOrders() {
        log.info("Просмотр всех заказов");
        List<Order> orders = orderRepository.findByOrderByDateDesc();
        log.info("Найдено {} заказов", orders.size());
        return orders;
    }

    @Override
    public List<Order> findWithPagination(int page, int size) {
        log.info("Просмотр заказов с пагинацией: страница = {}, размер = {}", page, size);
        List<Order> orders = orderRepository.findAll(PageRequest.of(page, size, Sort.by("date").descending())).getContent();
        log.info("Найдено {} заказов на странице {}", orders.size(), page);
        return orders;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String randomPrefix = Long.toHexString(System.currentTimeMillis()).substring(0, 4);
        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null) {
            originalFilename = originalFilename.replaceAll("\\s+", "_");
        }

        String filename = randomPrefix + "_" + originalFilename;
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.write(filePath, file.getBytes());

        log.info("Файл загружен: {}", filePath.toAbsolutePath());
        return "/uploads/" + filename;
    }
}