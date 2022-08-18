package ru.job4j_todo.service;

import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.persistence.AccountStore;
import net.jcip.annotations.ThreadSafe;
import ru.job4j_todo.service.interfaces.AccountServiceInterface;
import java.util.Optional;

@ThreadSafe
@Service
public class AccountServiceService implements AccountServiceInterface {

    private final AccountStore accountStore;

    public AccountServiceService(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    /**
     * Добавляет акк в БД
     * @param account - аккаунт для добавления
     * @return - возвращает обьект в виде контейнера optional
     */
    @Override
    public Optional<Account> addAccount(Account account) {
        return accountStore.addAccount(account);
    }

    /**
     * Выполняет поиск по двум критериям
     * @param login - эл.почта для входа
     * @param password - пароль для входа
     * @return возвращает контейнер optional
     * с обьектом Account который прошел
     * авторизацию
     */
    @Override
    public Optional<Account> findUserByLoginAndPwd(String login, String password) {
        return accountStore.findUserByLoginAndPwd(login, password);
    }



}
