package ru.job4j_todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.model.Item;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountStore {

    private final SessionFactory sessionFactory;

    public AccountStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Account> addAcc(Account account) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();
        session.close();
        return Optional.of(account);
    }

    public List<Account> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("from Account").list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Account> findAccById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("from Account a where a.id =: aId").setParameter("aId", id)
                .list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

}
