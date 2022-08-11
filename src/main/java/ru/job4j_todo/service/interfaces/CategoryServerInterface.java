package ru.job4j_todo.service.interfaces;

import ru.job4j_todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryServerInterface {


    Optional<Category> add(Category category);

    Optional<Category> findCategory(int id);

    List<Category> findAll();
}
