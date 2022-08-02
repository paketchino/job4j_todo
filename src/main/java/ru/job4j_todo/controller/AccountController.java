package ru.job4j_todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.service.AccountServiceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class AccountController {

    private final AccountServiceService accountService;

    public AccountController(AccountServiceService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/addAccount")
    public String addAcc(HttpSession session, Model model,
                         @RequestParam (name = "fail", required = false) Boolean fail ) {
        model.addAttribute("fail", fail != null);
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        return "addAccount";
    }

    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute Account account, HttpSession session, Model model) {
        Optional regAcc = accountService.addAccount(account);
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        if (regAcc.isEmpty()) {
            model.addAttribute("message", "Пользователь уже существует");
            return "redirect:/fail";
        }
        return "redirect:/loginPage";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        model.addAttribute("fail", fail != null);
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpServletRequest req, Model model) {
        Optional<Account> accountStore = accountService.findUserByLoginAndPwd(
                account.getLogin(), account.getPassword()
        );
        if (accountStore.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        session.setAttribute("account", accountStore.get());
        return "redirect:/allItems";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        session.invalidate();
        return "redirect:/loginPage";
    }

    @GetMapping("/fail")
    public String fail(HttpSession session, Model model) {
        FindUser findUser = new FindUser();
        findUser.findUser(session, model);
        return "fail";
    }
}
