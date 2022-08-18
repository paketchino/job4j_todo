package ru.job4j_todo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@EqualsAndHashCode
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table (name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "name_account")
    private String name;

    @JoinColumn (name = "login", unique = true)
    private String login;

    @JoinColumn(name = "password")
    private String password;


    public Account() {
    }

    @Override
    public String toString() {
        return String.format("Account - id =: %s, name =: %s, login =:%s, password =:%s",
                id, name, login, password);
    }
}
