package ru.job4j_todo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Setter
@Getter
@Entity
@Table (name = "items")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private boolean done;

    @ManyToOne (fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    public Item() {
    }

    public static Item of(int id, String name, String description, boolean done, Set<Category> categories) {
        Item item = new Item();
        item.id = id;
        item.name = name;
        item.description = description;
        item.created = new Date(System.currentTimeMillis());
        item.done = done;
        item.categories = categories;
        return item;
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
