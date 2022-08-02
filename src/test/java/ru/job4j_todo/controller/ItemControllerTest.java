package ru.job4j_todo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.AccountServiceService;
import ru.job4j_todo.service.ItemServiceService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Test
    public void whenAddItem() {
        List<Item> items = Arrays.asList(
                Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(),true, new Account())
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        ItemServiceService service = mock(ItemServiceService.class);
        AccountServiceService accountService = mock(AccountServiceService.class);
        Mockito.when(service.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(service, accountService);
        String page = itemController.items(session, model);
        Mockito.verify(model).addAttribute("items", items);
        assertThat(page, is("allItems"));
    }

    @Test
    public void whenCreateItem() {
        Account account = Account.of(10, "Sergey", "gay32", "1244");
        Item item35 = Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(),true, new Account());
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        ItemServiceService service = mock(ItemServiceService.class);
        AccountServiceService accountService = mock(AccountServiceService.class);
        ItemController itemController = new ItemController(service, accountService);
        String page = itemController.createItem(item35, session, model);
        verify(service).add(item35);
        assertThat(page, is("redirect:/allItems"));
    }


    @Test
    public void whenFindById() {
        List<Item> item35 = Arrays.asList(
                Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(), true, new Account()));
        HttpSession session = mock(HttpSession.class);
        ItemServiceService service = mock(ItemServiceService.class);
        AccountServiceService accountService = mock(AccountServiceService.class);
        ItemController itemController = new ItemController(service, accountService);
        Mockito.when(service.findById(35)).thenReturn(Optional.of(Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(), true,
                new Account())));
        assertThat(item35, is(service.findById(35)));
    }

    @Test
    public void whenItemDelete() {
        Item item35 = Item.of(35, "Очень важная задача", "Нужно сделать что то важное", LocalDateTime.now(), true, new Account());
        HttpSession session = mock(HttpSession.class);
        ItemServiceService service = mock(ItemServiceService.class);
        AccountServiceService accountService = mock(AccountServiceService.class);
        Model model = mock(Model.class);
        ItemController itemController = new ItemController(service, accountService);
        String page = itemController.deleteItem(item35, session, model);
        verify(service).remove(item35.getId());
        assertThat(page, is("redirect:/allItems"));
    }

}