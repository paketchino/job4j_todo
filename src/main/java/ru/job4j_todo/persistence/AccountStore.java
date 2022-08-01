package ru.job4j_todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Account;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class AccountStore {

    private final SessionFactory sessionFactory;

    public AccountStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Account> addAccount(Account account) {
        Optional<Account> rsl = Optional.empty();
        Account account1 = this.tx(
                session -> {
                    session.save(account);
                    return account;
                }
        );
        if (account.getId() != 0) {
            rsl = Optional.of(account1);
        }
        return rsl;
    }

    public List<Account> findAll() {
        return tx(session -> session.createQuery("from Account ").list());
    }

    public List<Account> findAccById(int id) {
        return tx(session ->
                session.createQuery("from Account a where a.id = :aId")
                        .setParameter("aId", id)
                        .list());
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
