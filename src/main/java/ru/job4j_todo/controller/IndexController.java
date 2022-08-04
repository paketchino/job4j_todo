package ru.job4j_todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.persistence.ItemStore;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class IndexController {

    private final ItemStore itemStore;

    public IndexController(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session, @ModelAttribute Account account) {
        FindUser.findUser(session, model);
        model.addAttribute("items", itemStore.findAll(account));
        return "index";
    }
}
