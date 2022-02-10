package luxoft.training.bookserver.repositories;

import luxoft.training.bookserver.model.Book;

import java.util.List;

public interface SimilarBookRepository {
    List<Book> findBookByNameContaining(List<String> bookNames);
}
