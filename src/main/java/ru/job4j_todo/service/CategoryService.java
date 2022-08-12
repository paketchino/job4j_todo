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

    public CategoryService(CategoryStore categoryStore) {
        this.categoryStore = categoryStore;
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
        return categoryStore.findAll();
    }
}
