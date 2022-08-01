package ru.job4j_todo.service;

import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.persistence.AccountStore;
import net.jcip.annotations.ThreadSafe;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class AccountServiceService implements AccountServiceInterface {

    private final AccountStore accountStore;

    public AccountServiceService(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    @Override
    public Optional<Account> addAccount(Account account) {
        return accountStore.addAccount(account);
    }

    @Override
    public Optional<Account> findUserByLoginAndPwd(String email, String password) {
        return find(email, password);
    }

    @Override
    public List<Account> findAccById(int id) {
        return accountStore.findAccById(id);
    }

    private Optional<Account> find(String login, String password) {
        Optional<Account> findUser = Optional.empty();
        List<Account> accounts = accountStore.findAll();
        for (Account account : accounts) {
            if (account.getLogin().equals(login)
                    && account.getPassword().equals(password)) {
                findUser = Optional.of(account);
            }
        }
        return findUser;
    }
}
