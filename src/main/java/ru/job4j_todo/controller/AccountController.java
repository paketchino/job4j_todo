package ru.job4j_todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
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

    @GetMapping("/addAccount")
    public String addAcc(HttpSession session, Model model,
                         @RequestParam (name = "fail", required = false) Boolean fail ) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("account",  new Account());
        return "addAccount";
    }

    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute Account account, HttpSession session, Model model) {
        Optional regAcc = accountService.addAcc(account);
        model.addAttribute("account", findUser(session));
        if (regAcc.isEmpty()) {
            model.addAttribute("message", "Пользователь уже существует");
            return "redirect:/fail";
        }
        return "redirect:/allItems";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        model.addAttribute("account", findUser(session));
        model.addAttribute("fail", fail != null);
        List<Item> itemsList = new ArrayList<>();
        model.addAttribute("account", Account.of(
                0, "Enter email", "Enter login", "Enter password", itemsList));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpServletRequest req) {
        Optional<Account> accountStore = accountService.findUserByLoginAndPwd(
                account.getLogin(), account.getPassword()
        );
        if (accountStore.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        findUser(session);
        session.setAttribute("account", accountStore.get());
        return "redirect:/allItems";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        model.addAttribute("account", findUser(session));
        session.invalidate();
        return "redirect:/loginPage";
    }

    @GetMapping("/fail")
    public String fail(HttpSession session, Model model) {
        model.addAttribute("account", findUser(session));
        return "fail";
    }
}
