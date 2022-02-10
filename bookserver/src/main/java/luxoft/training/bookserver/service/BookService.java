package luxoft.training.bookserver.service;

import luxoft.training.bookserver.model.Book;
import luxoft.training.bookserver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.IntStream.range;

@Component
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "bookCache", key = "#bookName")
    public List<Book> getBooksWithName(String bookName) {
        return Arrays.stream(bookName.split("\\s+")).map(str -> bookRepository.findBookByNameContaining(str))
                .reduce((books, books2) -> {
                    books.retainAll(books2);
                    return books;
                }).orElse(null);
    }

    @Transactional
    public void generateBooks(int count) {
        range(0, count).forEach(value -> addBook(new Book(UUID.randomUUID().toString())));
    }

    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }

}
