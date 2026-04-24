
import org.example.Main;
import org.example.Person;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testBasicInfo() {
        Main.inspect(Person.class, "-", 1);
        String out = output.toString();

        assertTrue(out.contains("Имя: org.example.Person"));
        assertTrue(out.contains("Тип: Class"));
    }

    @Test
    void testNullClass() {
        assertThrows(IllegalArgumentException.class, () -> {
            Main.inspect(null, "-", 1);
        });
    }


    @Test
    void testFields() {
        Main.inspect(Person.class, "-", 1);
        String out = output.toString();

        assertTrue(out.contains("Поля:"));
        assertTrue(out.contains("name"));
        assertTrue(out.contains("age"));
    }

    @Test
    void testMethods() {
        Main.inspect(Person.class, "-", 1);
        String out = output.toString();

        assertTrue(out.contains("Методы:"));
        assertTrue(out.contains("getName"));
        assertTrue(out.contains("setName"));
    }


    @Test
    void testConstructors() {
        Main.inspect(Person.class, "-", 1);
        String out = output.toString();

        assertTrue(out.contains("Конструкторы:"));
        assertTrue(out.contains("Person"));
    }
}