package luxoft.training.bookserver.rest;

import luxoft.training.bookserver.model.Book;
import luxoft.training.bookserver.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/similar/stream/{bookName}")
    public List<Book> getSimilarBooksViaStream(@PathVariable String bookName) {
        return bookService.getSimilarBooksViaStream(bookName);
    }

    @GetMapping("/similar/predicate/{bookName}")
    public List<Book> getSimilarBooksViaPredicate(@PathVariable String bookName) {
        return bookService.getSimilarBooksViaPredicate(bookName);
    }

    @PostMapping("/generate/{count}")
    public void generateBooks(@PathVariable int count) {
        bookService.generateBooks(count);
    }

    @PostMapping("/addBook")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

}
