package luxoft.training.bookserver.repositories;

import luxoft.training.bookserver.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SimilarBookRepositoryImpl implements SimilarBookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findBookByNameContaining(List<String> bookNames) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        Path<String> bookPath = root.get("name");

        List<Predicate> predicates = new ArrayList<>(bookNames.size());
        for (String bookName : bookNames) {
            predicates.add(cb.like(bookPath, "%" + bookName + "%"));
        }

        query.select(root).where(cb.or(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
