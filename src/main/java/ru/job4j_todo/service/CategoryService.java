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


    @Override
    public Optional<Category> add(Category category) {
        return categoryStore.add(category);
    }

    @Override
    public List<Category> findCategory(int id) {
        return categoryStore.findCategory(id);
    }

    @Override
    public List<Category> findAll() {
        itemServiceService.findAll().forEach(item ->
                item.getSets().forEach(category -> categoryStore.findCategory(category.getId())));
        return categoryStore.findAll();
    }
}
