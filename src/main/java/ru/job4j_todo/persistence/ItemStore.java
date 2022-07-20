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
        List<Item> list = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Item> findAllByCondition(boolean condition) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List listCondition = session.createQuery("from Item i where i.done = :fCondition")
                .setParameter("fCondition", condition).list();
        session.getTransaction().commit();
        session.close();
        return listCondition;
    }

    public Item add(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> findById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Item> findById = session.createQuery("from Item i where i.id = :fId").setParameter("fId", id).list();
        session.getTransaction().commit();
        session.close();
        return findById;
    }

}
