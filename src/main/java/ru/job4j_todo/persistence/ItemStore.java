package ru.job4j_todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;
import ru.job4j_todo.service.ItemServiceService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ThreadSafe
@Repository
public class ItemStore implements ItemStoreInterface {

    private final SessionFactory sessionFactory;

    public ItemStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Account> findAccount(Account account) {
        return tx(session -> session.createQuery("from Item i where i.account = :aAcc")
                .setParameter("aAcc", account).uniqueResultOptional());
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item")
                .list());
    }

    @Override
    public List<Item> findAllByCondition(boolean condition) {
        return tx(session -> session.createQuery("from Item i where i.done = :fCondition")
                .setParameter("fCondition", condition).list());
    }

    @Override
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

    @Override
    public Optional<Item> findById(int id) {
       return tx(session ->
            session.createQuery("from Item i where i.id = :fId").setParameter("fId", id)
                    .uniqueResultOptional());
    }

    @Override
    public boolean remove(int id) {
        return tx(session -> session.createQuery("delete from Item i where i.id = :fId")
                .setParameter("fId", id).executeUpdate() > 0);
    }

    @Override
    public List<Item> findAllByAccount(Account account) {
        return tx(session -> session.createQuery("from Item i where i.account.id = :fId")
                .setParameter("fId", account.getId()).list());
    }

    @Override
    public boolean update(Item item) {
        return tx(session -> session.createQuery("update Item i set i.name = :iName, i.description = :iDesc, i.done = :iFalse where i.id = :iId")
                .setParameter("iName", item.getName())
                .setParameter("iDesc", item.getDescription())
                .setParameter("iFalse", item.isDone())
                .setParameter("iId", item.getId()).executeUpdate() > 0);
    }

    @Override
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
