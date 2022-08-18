package ru.job4j_todo.persistence.interfaces;

import ru.job4j_todo.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountStoreInterface {

    Optional<Account> addAccount(Account account);

    Optional<Account> findUserByLoginAndPwd(String login, String password);

}
