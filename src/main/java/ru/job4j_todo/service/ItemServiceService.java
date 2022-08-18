package ru.job4j_todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.persistence.ItemStore;
import ru.job4j_todo.service.interfaces.ItemServiceInterface;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class ItemServiceService implements ItemServiceInterface {

    private ItemStore itemStore;

    public ItemServiceService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    /**
     * Возвращает список всех созданных заявок
     * @return - список всех заявок
     */
    @Override
    public List<Item> findAll() {
        return itemStore.findAll();
    }

    /**
     * Выполняет поиск по всем не выполненным заявкам
     * @return - список не выполненных заявок
     */
    @Override
    public List<Item> findAllByConditionFalse() {
        return itemStore.findAllByCondition(false);
    }

    /**
     * Выполняет поиск по всем выполненным заявка
     * done = true
     * @return список выполненных заявок
     */
    @Override
    public List<Item> findAllByConditionTrue() {
        return itemStore.findAllByCondition(true);
    }

    /**
     * Выполняет добавление заявки в БД
     * @param item - заявка на добавление
     * @return возвращает item в контейнере optional
     */
    @Override
    public Optional<Item> add(Item item) {
        return itemStore.add(item);
    }

    /**
     * Выполняет поиск заявки item по item.id
     * @param id - id заявки
     * @return возвращает item в контейнере optional
     */
    @Override
    public Optional<Item> findById(int id) {
        return itemStore.findById(id);
    }

    /**
     * Обновляет данные заявки и автоматически сбрасывает,
     * если заявка была выполнено done = true
     * @param item - текущая заявка которую нужно обновить
     * @return true || false при успешном обновление
     */
    @Override
    public boolean update(Item item) {
        return itemStore.update(item);
    }


    /**
     * Удаляет заявку по Id
     * @param id - заявка для удаления
     * @return true || false при успешном удаление
     */
    @Override
    public boolean remove(int id) {
        return itemStore.remove(id);
    }

    /**
     * Выполняет поиск акк по его account.id
     * @param account - текущий акк
     * @return account List<Account>
     */
    @Override
    public List<Item> findAllByAccount(Account account) {
        return itemStore.findAllByAccount(account);
    }


    /**
     * Меняет аттребут в заявки item.done на true
     * @param item - текущая заявка
     * @return true || false при успешной операции
     */
    @Override
    public boolean updateCondition(Item item) {
        return itemStore.updateCondition(item);
    }
}
