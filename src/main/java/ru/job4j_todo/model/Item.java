package ru.job4j_todo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "items")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    private boolean done;

    public Item() {
    }
    public static Item of(int id, String name, String description, LocalDateTime created, boolean done, Account account) {
        Item item = new Item();
        item.id = id;
        item.name = name;
        item.description = description;
        item.created = created;
        item.done = done;
        item.account = account;
        return item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id && done == item.done
                && Objects.equals(description, item.description)
                && Objects.equals(name, item.name)
                && Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done, account);
    }

    @Override
    public String toString() {
        return String.format("Item - id: = %s, name: = %s, descItem: = %s, created: = %s, boolean: = %s",
                id, name, description, created, done);
    }
}
