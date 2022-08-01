package ru.job4j_todo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table (name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column (unique = true)
    private String login;

    private String password;


    public Account() {
    }

    public static Account of(int id, String name, String login, String password) {
        Account account = new Account();
        account.id = id;
        account.name = name;
        account.login = login;
        account.password = password;
        return account;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id && Objects.equals(name, account.name)
                && Objects.equals(login, account.login)
                && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, password);
    }

    @Override
    public String toString() {
        return String.format("Account - id =: %s, name =: %s, login =:%s, password =:%s",
                id, name, login, password);
    }
}
