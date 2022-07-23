package ru.job4j_todo.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.persistence.ItemStore;
import ru.job4j_todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Test
    public void whenAddItem() {
        List<Item> items = Arrays.asList(
                Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(),true)
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        ItemService service = mock(ItemService.class);
        Mockito.when(service.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(service);
        String page = itemController.items(session, model);
        Mockito.verify(model).addAttribute("items", items);
        assertThat(page, is("allItems"));
    }

    @Test
    public void whenCreateItem() {
        Item item35 = Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(),true);
        HttpSession session = mock(HttpSession.class);
        ItemService service = mock(ItemService.class);
        ItemController itemController = new ItemController(service);
        String page = itemController.createItem(item35, session);
        verify(service).add(item35);
        assertThat(page, is("redirect:/allItems"));
    }


    @Test
    public void whenFindById() {
        List<Item> item35 = Arrays.asList(
                Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(), true));
        HttpSession session = mock(HttpSession.class);
        ItemService service = mock(ItemService.class);
        ItemController itemController = new ItemController(service);
        Mockito.when(service.findById(35)).thenReturn(Optional.ofNullable(item35.get(35)));
        assertThat(item35, is(service.findById(35)));
    }

    @Test
    public void whenItemDelete() {
        Item item35 = Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(), true);
        HttpSession session = mock(HttpSession.class);
        ItemService service = mock(ItemService.class);
        ItemController itemController = new ItemController(service);
        String page = itemController.deleteItem(item35, session);
        verify(service).remove(item35.getId());
        assertThat(page, is("redirect:/allItems"));
    }

}