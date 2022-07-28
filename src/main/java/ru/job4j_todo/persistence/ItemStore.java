package ru.job4j_todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Account;
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

    public Optional<Item> findById(int id) {
        Optional<Item> item = Optional.empty();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Item findById = (Item) session.createQuery("from Item i where i.id = :fId").setParameter("fId", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(findById);
    }

    public boolean remove(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Item i where i.id = :fId").setParameter("fId", id);
        boolean rsl = query.executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public boolean update(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update Item i set i.name = :iName, i.description = :iDesc where i.id = :iId")
                .setParameter("iName", item.getName())
                .setParameter("iDesc", item.getDescription())
                .setParameter("iId", item.getId());
        boolean rsl = query.executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public boolean updateCondition(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update Item i set i.done = :iDone where i.id = :iId")
                .setParameter("iDone", item.isDone())
                .setParameter("iId", item.getId());
        boolean rsl = query.executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

}
