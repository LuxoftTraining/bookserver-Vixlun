package luxoft.training.bookserver;

import luxoft.training.bookserver.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final BookService bookService;

    @Autowired
    public DataLoader(BookService bookService) {
        this.bookService = bookService;
    }

    @Value("${db.initialization.books.count:1000}")
    private Integer initializationBooksCount;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bookService.generateBooks(initializationBooksCount);
    }
}
