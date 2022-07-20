package ru.job4j_todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/allItems")
    public String items(HttpSession session, Model model) {
        model.addAttribute("items", itemService.findAll());
        return "allItems";
    }

    @GetMapping("/addItem")
    public String addItem(HttpSession session, Model model) {
        model.addAttribute("item", Item.of(0, "Enter item" ,"Enter desc", new Date(), false));
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, HttpSession session) {
        itemService.add(item);
        return "redirect:/allItems";
    }

    @GetMapping("/doneItems")
    public String doneItems(HttpSession session, Model model) {
        model.addAttribute("items", itemService.findAllByConditionTrue());
        return "doneItems";
    }

    @GetMapping("/undoneItems")
    public String undoneItems(HttpSession session, Model model) {
        model.addAttribute("items", itemService.findAllByConditionFalse());
        return "undoneItems";
    }

    @GetMapping("/item/{itemId}")
    public String taskItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("itemById", itemService.findById(id));
        return "item";
    }


}
