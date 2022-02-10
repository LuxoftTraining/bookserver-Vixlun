package luxoft.training.bookserver;

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

    @GetMapping("/similar/{bookName}")
    public List<Book> getBooksWithName(@PathVariable String bookName) {
        return bookService.getBooksWithName(bookName);
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
