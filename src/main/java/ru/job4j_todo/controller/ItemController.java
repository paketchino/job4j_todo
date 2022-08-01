package ru.job4j_todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.AccountService;
import ru.job4j_todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class ItemController {

    private final ItemService itemService;
    private final AccountService accountService;

    public ItemController(ItemService itemService, AccountService accountService) {
        this.itemService = itemService;
        this.accountService = accountService;
    }

    public Account findUser(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        return account;
    }

    public void findUser(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
    }

    @GetMapping("/allItems")
    public String items(HttpSession session, Model model) {
        findUser(session, model);
        model.addAttribute("items", itemService.findAll());
        return "allItems";
    }

    @GetMapping("/addItem")
    public String addItem(HttpSession session, Model model, Account account) {
        findUser(session);

        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, HttpSession session) {
        findUser(session);
        itemService.add(item);
        return "redirect:/allItems";
    }

    @GetMapping("/doneItems")
    public String doneItems(HttpSession session, Model model, Account account) {
        findUser(session);
        model.addAttribute("account", account);
        model.addAttribute("items", itemService.findAllByConditionTrue());
        return "doneItems";
    }

    @GetMapping("/undoneItems")
    public String undoneItems(HttpSession session, Model model, Account account) {
        findUser(session);
        model.addAttribute("account", account);
        model.addAttribute("items", itemService.findAllByConditionFalse());
        return "undoneItems";
    }

    @GetMapping("/item/{itemId}")
    public String taskItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("itemById", itemService.findById(id));
        return "item";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(Model model, HttpSession session, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "deleteItem";
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@ModelAttribute Item item, HttpSession session) {
        itemService.remove(item.getId());
        return "redirect:/allItems";
    }

    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/allItems";
    }

    @GetMapping("/successItem/{itemId}")
    public String successItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("item", itemService.findById(id));
        return "successItem";
    }

    @PostMapping("/successItem")
    public String doneItem(@ModelAttribute Item item) {
        item.setDone(true);
        itemService.updateCondition(item);
        return "redirect:/doneItems";
    }

}
