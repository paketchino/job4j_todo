package ru.job4j_todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("items")
    public String items(HttpSession session, Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @GetMapping("addItem")
    public String addItem(HttpSession session, Model model, Item item) {
        model.addAttribute("addItem", Item.of(0,
                "Enter name Item",
                "Add description",
                Timestamp.valueOf(LocalDateTime.now()),
                true));
        return "addItem";
    }

    @PostMapping("createItem")
    public String createItem(@ModelAttribute Item item, HttpSession session) {
        itemService.add(item);
        return "redirect:/items";
    }
}
