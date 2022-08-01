package ru.job4j_todo.persistence;

import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStoreInterface {

    List<Item> findAll();

    Optional<Item> add(Item item);

    List<Item> findAllByCondition(boolean condition);

    Optional<Item> findById(int id);

    boolean remove(int id);

    List<Item> findAllByAccount(Account account);

    boolean update(Item item);

    boolean updateCondition(Item item);

}
