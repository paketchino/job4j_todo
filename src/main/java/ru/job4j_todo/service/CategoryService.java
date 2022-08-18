package ru.job4j_todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Category;
import ru.job4j_todo.persistence.CategoryStore;
import ru.job4j_todo.service.interfaces.CategoryServerInterface;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class CategoryService implements CategoryServerInterface {

    private final CategoryStore categoryStore;
    private final ItemServiceService itemServiceService;

    public CategoryService(CategoryStore categoryStore, ItemServiceService itemServiceService) {
        this.categoryStore = categoryStore;
        this.itemServiceService = itemServiceService;
    }


    /**
     * Добавляет категорию в БД
     * @param category - категория для добавления
     * "Важное", "Семейное"
     * @return - контейнер optional в котором хранится
     *  обьект категории
     */
    @Override
    public Optional<Category> add(Category category) {
        return categoryStore.add(category);
    }

    /**
     * Выполняет поиск категории по id
     * @param id - id для поиска
     * @return категорию в виде списка
     */
    @Override
    public List<Category> findCategory(int id) {
        return categoryStore.findCategory(id);
    }

    /**
     * Возвращает список созданных категория
     * @return список категорий
     */
    @Override
    public List<Category> findAll() {
        return categoryStore.findAll();
    }
}
