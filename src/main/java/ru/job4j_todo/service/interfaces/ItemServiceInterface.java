package ru.job4j_todo.service.interfaces;

import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemServiceInterface {

    List<Item> findAll();

    List<Item> findAllByConditionFalse();

    List<Item> findAllByConditionTrue();

    Optional<Item> add(Item item);

    Optional<Item> findById(int id);

    boolean update(Item item);

    boolean remove(int id);

    boolean updateCondition(Item item);

    List<Item> findAllByAccount(Account account);
}
