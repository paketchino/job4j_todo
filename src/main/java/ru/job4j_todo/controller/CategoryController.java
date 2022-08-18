package ru.job4j_todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j_todo.model.Category;
import ru.job4j_todo.service.CategoryService;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Страница добавления категории
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @return ссылка на страницу addCategory
     */
    @GetMapping("addCategory")
    public String addCategory(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        return "addCategory";
    }

    /**
     * Список всех категорий
     * @param session - текущая сессия которая сохраняет данные
     * и отправляет их на сервер
     * @param model - обрабатывает полученные данные
     * @return ссылку на страницу categories
     */
    @GetMapping("categories")
    public String categories(HttpSession session, Model model) {
        FindUser.findUser(session, model);
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    /**
     * Сохранение категории в БД
     * @param category - обьект категория
     * @return на страницу со списком categories
     */
    @PostMapping("createCategory")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.add(category);
        return "redirect:categories";
    }

}
