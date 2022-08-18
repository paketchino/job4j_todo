package ru.job4j_todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Category;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.AccountServiceService;
import ru.job4j_todo.service.CategoryService;
import ru.job4j_todo.service.ItemServiceService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ItemController {

    private final ItemServiceService itemService;
    private final AccountServiceService accountService;

    private final CategoryService categoryService;

    public ItemController(ItemServiceService itemService, AccountServiceService accountService, CategoryService categoryService) {
        this.itemService = itemService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }


    /**
     * Список всех заявок
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @return ссылка на страницу allItems
     */
    @GetMapping("/allItems")
    public String items(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        model.addAttribute("items", itemService.findAll());
        return "allItems";
    }

    /**
     * Страница addItem
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @return ссылка на страницу addItem
     */
    @GetMapping("/addItem")
    public String addItem(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        model.addAttribute("categories", categoryService.findAll());
        return "addItem";
    }

    /**
     * Добавляет заявку в БД
     * @param item - заявка для добавления
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param categoryId - категории Id
     * @return ссылка на страницу allItems
     */
    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item,
                             HttpSession session,
                             @RequestParam (name = "categoryId") int categoryId) {
        item.setAccount((Account) session.getAttribute("account"));
        Set<Category> categories = new HashSet<>();
        item.setCreated(new Date(System.currentTimeMillis()));
        for (Category c : categoryService.findCategory(categoryId)) {
            categories.add(c);
        }
        item.setCategories(categories);
        itemService.add(item);
        return "redirect:/allItems";
    }

    /**
     *  Выполненные заявки item.done = true
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @return ссылка на страницу doneItems
     */
    @GetMapping("/doneItems")
    public String doneItems(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        model.addAttribute("items", itemService.findAllByConditionTrue());
        return "doneItems";
    }

    /**
     * Невыполненные заявки item.done = false
     * @param session - текущая сессия которая сохраняет данные
     *  и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @return ссылка на страницу undoneItems
     */
    @GetMapping("/undoneItems")
    public String undoneItems(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        model.addAttribute("items", itemService.findAllByConditionFalse());
        return "undoneItems";
    }

    /**
     * Переход на ссылку определенной заявки
     * @param model - обрабатывает полученные данные
     * @param id - номер созданной заявки
     * @param session - текущая сессия которая сохраняет данные
     *  и отправляет их на сервер
     * @return возвращает ссылку на страницу item
     */
    @GetMapping("/item/{itemId}")
    public String taskItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        FindUser.findUser(session, model);
        model.addAttribute("itemById", itemService.findById(id).get());
        return "item";
    }

    /**
     * Страница для удаления выбранной заявки с номером id
     * @param model - обрабатывает полученные данные
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param id - номер заявки для удаления
     * @return возвращает ссылку на страницу deleteItem
     */
    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(Model model, HttpSession session, @PathVariable("itemId") int id) {
        FindUser.findUser(session, model);
        model.addAttribute("item", itemService.findById(id));
        return "deleteItem";
    }

    /**
     * Удаление заявки по id из БД
     * @param item - заявка для удаления
     *             удаление происходит по item.id
     * @return возвращает ссылку на страницу allItems
     */
    @PostMapping("/deleteItem/deleteItem")
    public String deleteItem(@ModelAttribute Item item) {
        itemService.remove(item.getId());
        return "redirect:/allItems";
    }

    /**
     * Страница для изменения информации о заявке
     * @param model - обрабатывает полученные данные
     * @param id - номер заявки
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @return возвращает ссылку на страницу updateItem
     */
    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        FindUser.findUser(session, model);
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    /**
     * Обновление заявки в БД
     * @param item - заявка в которой будут происходить изменения
     * @return возвращает ссылку на страницу allItems
     */
    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        item.setDone(false);
        itemService.update(item);
        return "redirect:/allItems";
    }

    /**
     * Страница для подтверждения
     * @param model - обрабатывает полученные данные
     * @param id - номер заявки
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @return возвращает ссылку на страницу successItem
     */
    @GetMapping("/successItem/{itemId}")
    public String successItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        FindUser.findUser(session, model);
        model.addAttribute("item", itemService.findById(id));
        return "successItem";
    }

    /**
     * Изменение информации о заявки в БД на item.true
     * @param item - заявка для подтверждения
     * @return возвращает ссылку на страницу successItem
     */

    @PostMapping("/successItem")
    public String doneItem(@ModelAttribute Item item) {
        item.setDone(true);
        itemService.updateCondition(item);
        return "redirect:/doneItems";
    }

}
