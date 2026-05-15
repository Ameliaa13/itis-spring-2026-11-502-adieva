import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalDouble;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {


    @Test
    void testSolve3(){
        List<Book> books = List.of(
                new Book("Book1", 300, 2020, "Author1", List.of("fantasy")),
                new Book ("Book2", 500, 1999, "Author2", List.of( "romantic"))
        );

        OptionalDouble avg = Main.solve3(books);
        assertFalse(avg.isPresent());
    }

    @Test
    void test2Solve3(){
        List<Book> books = List.of(
                new Book("Book1", 300, 1999, "Author1", List.of("fantasy")),
                new Book ("Book2", 500, 1989, "Author2", List.of( "romantic"))
        );
        OptionalDouble avg = Main.solve3(books);
        assertTrue(avg.isPresent());
    }


}



