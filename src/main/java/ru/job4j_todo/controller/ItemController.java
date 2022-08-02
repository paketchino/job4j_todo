package ru.job4j_todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.AccountServiceService;
import ru.job4j_todo.service.ItemServiceService;

import javax.servlet.http.HttpSession;

@Controller
public class ItemController {

    private final ItemServiceService itemService;
    private final AccountServiceService accountService;

    public ItemController(ItemServiceService itemService, AccountServiceService accountService) {
        this.itemService = itemService;
        this.accountService = accountService;
    }


    @GetMapping("/allItems")
    public String items(HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("items", itemService.findAll());
        return "allItems";
    }

    @GetMapping("/addItem")
    public String addItem(HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        itemService.add(item);
        return "redirect:/allItems";
    }

    @GetMapping("/doneItems")
    public String doneItems(HttpSession session, Model model, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("items", itemService.findAllByConditionTrue());
        return "doneItems";
    }

    @GetMapping("/undoneItems")
    public String undoneItems(HttpSession session, Model model, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("items", itemService.findAllByConditionFalse());
        return "undoneItems";
    }

    @GetMapping("/item/{itemId}")
    public String taskItem(Model model, @PathVariable("itemId") int id, HttpSession session, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("itemById", itemService.findById(id).get());
        return "item";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(Model model, HttpSession session, @PathVariable("itemId") int id, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("item", itemService.findById(id));
        return "deleteItem";
    }

    @PostMapping("/deleteItem/deleteItem")
    public String deleteItem(@ModelAttribute Item item, HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        itemService.remove(item.getId());
        return "redirect:/allItems";
    }

    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id, HttpSession session, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, HttpSession session, Model model, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        item.setDone(false);
        itemService.update(item);
        return "redirect:/allItems";
    }

    @GetMapping("/successItem/{itemId}")
    public String successItem(Model model, @PathVariable("itemId") int id, HttpSession session, Account account) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        model.addAttribute("item", itemService.findById(id));
        return "successItem";
    }

    @PostMapping("/successItem")
    public String doneItem(@ModelAttribute Item item, HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        item.setDone(true);
        itemService.updateCondition(item);
        return "redirect:/doneItems";
    }

}
