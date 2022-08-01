package ru.job4j_todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    public void findUser(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session, Account account) {
        findUser(session);
        model.addAttribute("account", account);
        model.addAttribute("items", itemStore.findAll());
        return "index";
    }
}
