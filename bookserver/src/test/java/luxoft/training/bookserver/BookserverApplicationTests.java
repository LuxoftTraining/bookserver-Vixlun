package luxoft.training.bookserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import luxoft.training.bookserver.model.Book;
import luxoft.training.bookserver.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookserverApplicationTests {

    @Autowired
    private BookRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
       repository.deleteAll();
    }


    @Test
    public void test_get_book() throws Exception {
        repository.save(new Book("Java Performance by Vladimir Sonkin, 2022"));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        String jsonString = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        List<Book> result = new ObjectMapper().readValue(jsonString, new TypeReference<List<Book>>(){});

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Java Performance by Vladimir Sonkin, 2022", result.get(0).getName());
    }

    @Test
    public void test_get_similar() throws Exception {
        repository.save(new Book("Java Performance by Vladimir Sonkin, 2022"));
        repository.save(new Book("test book"));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        List<String> names = List.of("Java", "Performance", "Sonkin Java", "Java Sonkin");
        for(String bookName : names) {
            String jsonString = mockMvc.perform(MockMvcRequestBuilders.get("/similar/" + bookName))
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            List<Book> result = new ObjectMapper().readValue(jsonString, new TypeReference<List<Book>>(){});

            Assertions.assertNotNull(result);
            Assertions.assertFalse(result.isEmpty());
            Assertions.assertEquals("Java Performance by Vladimir Sonkin, 2022", result.get(0).getName());
        }

    }

}
