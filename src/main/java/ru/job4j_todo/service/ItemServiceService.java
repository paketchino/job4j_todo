package ru.job4j_todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.persistence.ItemStore;
import ru.job4j_todo.service.interfaces.ItemServiceInterface;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class ItemServiceService implements ItemServiceInterface {

    private ItemStore itemStore;

    public ItemServiceService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    @Override
    public Optional<Account> findAccount(Account account) {
        return itemStore.findAccount(account);
    }

    @Override
    public List<Item> findAll() {
        return itemStore.findAll();
    }

    @Override
    public List<Item> findAllByConditionFalse() {
        return itemStore.findAllByCondition(false);
    }

    @Override
    public List<Item> findAllByConditionTrue() {
        return itemStore.findAllByCondition(true);
    }

    @Override
    public Optional<Item> add(Item item) {
        return itemStore.add(item);
    }

    @Override
    public Optional<Item> findById(int id) {
        return itemStore.findById(id);
    }

    @Override
    public boolean update(Item item) {
        return itemStore.update(item);
    }

    @Override
    public boolean remove(int id) {
        return itemStore.remove(id);
    }

    @Override
    public List<Item> findAllByAccount(Account account) {
        return itemStore.findAllByAccount(account);
    }

    @Override
    public boolean updateCondition(Item item) {
        return itemStore.updateCondition(item);
    }
}
