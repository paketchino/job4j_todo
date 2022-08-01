package ru.job4j_todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ItemStore {

    private final SessionFactory sessionFactory;

    public ItemStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item").list());
    }

    public List<Item> findAllByCondition(boolean condition) {
        return tx(session -> session.createQuery("from Item i where i.done = :fCondition")
                .setParameter("fCondition", condition).list());
    }

    public Optional<Item> add(Item item) {
        Optional<Item> optionalItem = Optional.empty();
        Item item1 = this.tx(session -> {
            session.save(item);
            return item;
        });
        if (item1.getId() != 0) {
            optionalItem = Optional.of(item);
        }
        return optionalItem;
    }

    public List<Item> findById(int id) {
        return tx(session ->
            session.createQuery("from Item i where i.id = :fId").setParameter("fId", id).list());
    }

    public boolean remove(int id) {
        return tx(session -> session.createQuery("delete from Item i where i.id = :fId")
                .setParameter("fId", id).executeUpdate() > 0);
    }

    public List<Item> findAllByAccount(Account account) {
        return tx(session -> session.createQuery("from Item i where i.account.id = :fId")
                .setParameter("fId", account.getId()).list());
    }

    public boolean update(Item item) {
        return tx(session -> session.createQuery("update Item i set i.name = :iName, i.description = :iDesc where i.id = :iId")
                .setParameter("iName", item.getName())
                .setParameter("iDesc", item.getDescription())
                .setParameter("iId", item.getId()).executeUpdate() > 0);
    }

    public boolean updateCondition(Item item) {
        return tx(session -> session.createQuery("update Item i set i.done = :iDone where i.id = :iId")
                .setParameter("iDone", item.isDone())
                .setParameter("iId", item.getId()).executeUpdate() > 0);
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
