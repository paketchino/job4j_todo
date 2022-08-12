package ru.job4j_todo.persistence.interfaces;

import ru.job4j_todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryInterface {

    Optional<Category> add(Category category);

    List<Category> findCategory(int id);

    List<Category> findAll();

}
