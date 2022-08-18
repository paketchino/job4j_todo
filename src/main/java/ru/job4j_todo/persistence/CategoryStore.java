package ru.job4j_todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j_todo.model.Category;
import ru.job4j_todo.persistence.interfaces.CategoryInterface;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CategoryStore implements CategoryInterface {

    private final SessionFactory sessionFactory;

    public CategoryStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Добавляет категорию в БД
     * @param category - категория для добавления
     * "Важное", "Семейное"
     * @return - контейнер optional в котором хранится
     *  обьект категории
     */
    @Override
    public Optional<Category> add(Category category) {
        Optional<Category> rsl = Optional.empty();
        Category category1 = this.tx(session -> {
            session.save(category);
            return category;
        });
        if (category.getId() != 0) {
            rsl = Optional.of(category1);
        }
        return rsl;
    }

    /**
     * Выполняет поиск категории по id
     * @param id - id для поиска
     * @return категорию в виде списка
     */
    @Override
    public List<Category> findCategory(int id) {
        return tx(
                session ->
                        session.createQuery("from Category c where c.id = :cId")
                                .setParameter("cId", id).list()
        );
    }

    /**
     * Возвращает список созданных категория
     * @return список категорий
     */
    @Override
    public List<Category> findAll() {
        return tx(session ->
                session.createQuery("from Category ").list());
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
