package luxoft.training.bookserver;

import luxoft.training.bookserver.model.Book;
import luxoft.training.bookserver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/similar/{bookName}")
    public List<Book> getBooksWithName(@PathVariable String bookName) {
        return Arrays.stream(bookName.split("\\s+")).map(str -> bookRepository.findBookByNameContaining(str))
                .reduce((books, books2) -> {
                    books.retainAll(books2);
                    return books;
                }).orElse(null);
    }

    @PostMapping("/generate/{count}")
    public void generateBooks(@PathVariable int count) {
        bookRepository.saveAll(range(0, count)
                .mapToObj(value -> new Book(UUID.randomUUID().toString())).collect(toList()));
    }

    @PostMapping("/addBook")
    public void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

}
