package ru.job4j_todo.service;

import org.springframework.stereotype.Service;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.persistence.AccountStore;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountStore accountStore;

    public AccountService(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    public Optional<Account> addAcc(Account account) {
        return accountStore.addAcc(account);
    }

    public Optional<Account> findUserByLoginAndPwd(String email, String password) {
        return find(email, password);
    }

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
