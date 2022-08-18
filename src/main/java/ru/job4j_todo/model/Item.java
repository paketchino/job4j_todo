package ru.job4j_todo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Setter
@Getter
@Entity
@Table (name = "items")
@AllArgsConstructor
@EqualsAndHashCode
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "item_name")
    private String name;

    @JoinColumn(name = "item_desc")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JoinColumn(name = "item_done")
    private boolean done;

    @ManyToOne (fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    public Item() {
    }

    @Override
    public String toString() {
        return String.format("Item - id: = %s, name: = %s, descItem: = %s, created: = %s, boolean: = %s",
                id, name, description, created, done);
    }
}
