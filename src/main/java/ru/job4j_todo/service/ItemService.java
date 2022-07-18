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

    public void add(Item item) {
        itemStore.add(item);
    }
}
