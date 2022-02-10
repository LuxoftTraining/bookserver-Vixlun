package luxoft.training.bookserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter @ToString
@Table(indexes = @Index(columnList = "name"))
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }
}
