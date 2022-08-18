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

@Controller
@ThreadSafe
public class AccountController {

    private final AccountServiceService accountService;

    public AccountController(AccountServiceService accountService) {
        this.accountService = accountService;
    }


    /**
     * Страница регистрации нового аккаунта
     * @param session - текущая сессия которая сохраняет данные
     *                и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @param fail - при добавление произошла ошибка
     *             возвращается false
     * @return ссылка на страницу
     */
    @GetMapping("/addAccount")
    public String addAcc(HttpSession session, Model model,
                         @RequestParam (name = "fail", required = false) Boolean fail ) {
        model.addAttribute("fail", fail != null);
        FindUser.findUser(session, model);
        return "addAccount";
    }

    /**
     * Сохранение нового аккаунта в БД
     * @param account - аккаунт для сохранения
     * @param model - обрабатывает полученные данные
     * @return ссылку на страницу авторизаци
     */
    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute Account account, Model model) {
        Optional regAcc = accountService.addAccount(account);
        if (regAcc.isEmpty()) {
            model.addAttribute("message", "Пользователь уже существует");
            return "redirect:/fail";
        }
        return "redirect:/loginPage";
    }

    /**
     * В случае если вход произошел неуспешно
     * @param model - обрабатывает полученные данные
     * @param fail - ссылка на параметр в случаее неудачной авторизации
     * @param session - текущая сессия которая сохраняет данные
     *                и отправляет их на сервер
     * @return ссылку на страницу авторизации
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        model.addAttribute("fail", fail != null);
        FindUser.findUser(session, model);
        return "login";
    }

    /**
     * Вход на сайт с помощью вызова логина и пароля
     * @param account - текущий аккаунт
     * @param req - запрос на получение информации от сервера
     * @return в случае авторизации возвращает на страницу
     * с заявками
     */
    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpServletRequest req) {
        Optional<Account> accountStore = accountService.findUserByLoginAndPwd(
                account.getLogin(), account.getPassword()
        );
        if (accountStore.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("account", accountStore.get());
        return "redirect:/allItems";
    }

    /**
     * Если авторизация не произошла, то пользователя отправят на страницу авторизации
     * или пользователь захотел выйти
     * @param session - текущая сессия
     * @param model - обрабатывает полученные данные
     * @return ссылка на страницу авторизации
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        session.invalidate();
        return "redirect:/loginPage";
    }

    /**
     * Неудачная регистрация
     * @return ссылка на страницу
     */
    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }
}
