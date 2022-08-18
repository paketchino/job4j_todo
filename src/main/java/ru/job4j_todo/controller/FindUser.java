package ru.job4j_todo.controller;

import org.springframework.ui.Model;
import ru.job4j_todo.model.Account;

import javax.servlet.http.HttpSession;

public class FindUser {

    /**
     * Выполняет поиск пользователя который зашел на сайт
     * если у пользователя отсутствует аккаунт, то он заходит как
     * гость и для дальнейшей посещения сайта необходима авторизация
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     */
    public static void findUser(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        model.addAttribute("account", account);
    }
}
