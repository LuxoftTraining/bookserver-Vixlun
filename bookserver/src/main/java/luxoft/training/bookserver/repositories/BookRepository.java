package luxoft.training.bookserver.repositories;

import luxoft.training.bookserver.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, SimilarBookRepository {
    List<Book> findBookByNameContaining(String title);
}
