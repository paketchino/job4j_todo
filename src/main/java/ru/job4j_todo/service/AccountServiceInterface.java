package ru.job4j_todo.service;

import ru.job4j_todo.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountServiceInterface {

    Optional<Account> addAccount(Account account);

    Optional<Account> findUserByLoginAndPwd(String email, String password);

    List<Account> findAccById(int id);


}
