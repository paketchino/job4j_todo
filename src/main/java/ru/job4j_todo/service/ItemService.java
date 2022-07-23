package ru.job4j_todo.service;

import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.persistence.ItemStore;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public List<Item> findAll() {
        return itemStore.findAll();
    }

    public List<Item> findAllByConditionFalse() {
        return itemStore.findAllByCondition(false);
    }

    public List<Item> findAllByConditionTrue() {
        return itemStore.findAllByCondition(true);
    }

    public Item add(Item item) {
        return itemStore.add(item);
    }

    public Optional<Item> findById(int id) {
        return itemStore.findById(id);
    }

    public boolean update(Item item) {
        return itemStore.update(item);
    }
    public boolean remove(int id) {
        return itemStore.remove(id);
    }

    public boolean updateCondition(Item item) {
        return itemStore.updateCondition(item);
    }
}
