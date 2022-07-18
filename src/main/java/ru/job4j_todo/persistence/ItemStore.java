package ru.job4j_todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemStore {

    private final SessionFactory sessionFactory;

    public ItemStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Item> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Item");
        List list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public void add(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

}
