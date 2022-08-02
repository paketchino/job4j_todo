package ru.job4j_todo.controller;

import org.springframework.ui.Model;
import ru.job4j_todo.model.Account;

import javax.servlet.http.HttpSession;

public class FindUser {

    public void findUser(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
    }
}
