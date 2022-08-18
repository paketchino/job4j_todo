package ru.job4j_todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Account;
import ru.job4j_todo.persistence.interfaces.AccountStoreInterface;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@ThreadSafe
public class AccountStore implements AccountStoreInterface {

    private final SessionFactory sessionFactory;

    public AccountStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Добавляет акк в БД
     * @param account - аккаунт для добавления
     * @return - возвращает обьект в виде контейнера optional
     */
    @Override
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

    /**
     * Выполняет поиск по двум критериям
     * @param login - эл.почта для входа
     * @param password - пароль для входа
     * @return возвращает контейнер optional
     * с обьектом Account который прошел
     * авторизацию
     */
    @Override
    public Optional<Account> findUserByLoginAndPwd(String login, String password) {
        return tx(session -> session.createQuery("from Account a where a.login = :aLogin and a.password =: aPassword")
                .setParameter("aLogin", login)
                .setParameter("aPassword", password)
                .uniqueResultOptional());
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
