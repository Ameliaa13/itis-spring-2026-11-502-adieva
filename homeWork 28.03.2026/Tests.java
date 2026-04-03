import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private static final String TEST_LOG = """
        2025-03-27 08:00:01.123 [main] INFO  com.example.Application - Starting application...
        2025-03-27 08:00:16.555 [qtp123456-19] WARN  com.example.api.UserController - Invalid request parameter: 'sort' value 'invalid'
        2025-03-27 08:00:01.456 [main] DEBUG com.example.ConfigLoader - Loading configuration from application.yml
        2025-03-27 08:00:17.888 [qtp123456-20] ERROR com.example.api.PaymentController - Payment processing failed: GatewayTimeoutException
        2025-03-27 08:00:17.777 [qtp123456-20] SEVERE com.example.service.PaymentService - Payment gateway timeout after 30 seconds
        2025-03-27 08:00:02.678 [main] INFO  com.example.HttpServer - Starting embedded Jetty on port 8080
        """;

    @TempDir
    static Path tempDir;
    private static Path testLogFile;

    @BeforeAll
    static void setUp() throws IOException {
        testLogFile = tempDir.resolve("test.log");
        Files.writeString(testLogFile, TEST_LOG);
    }


    static class LogAnalyzer {
        public static List<String> solve(String file) throws IOException {
            List<String> result = new java.util.ArrayList<>();
            Pattern pattern = Pattern.compile("\\b(WARN|ERROR|SEVERE)\\b");

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pattern.matcher(line).find()) {
                        result.add(line);
                    }
                }
            }
            return result;
        }
    }

    @Test
    @DisplayName("Должен найти все записи с WARN, ERROR, SEVERE")
    void testFindAllWarnAndAbove() throws IOException {
        List<String> result = LogAnalyzer.solve(testLogFile.toString());

        assertEquals(3, result.size());
        assertTrue(result.get(0).contains("WARN"));
        assertTrue(result.get(1).contains("ERROR"));
        assertTrue(result.get(2).contains("SEVERE"));
    }

    @Test
    @DisplayName("Не должен находить INFO и DEBUG записи")
    void testShouldNotFindInfoAndDebug() throws IOException {
        List<String> result = LogAnalyzer.solve(testLogFile.toString());

        assertFalse(result.stream().anyMatch(line -> line.contains("INFO")));
        assertFalse(result.stream().anyMatch(line -> line.contains("DEBUG")));
    }

    @Test
    @DisplayName("Пустой файл должен возвращать пустой список")
    void testEmptyFile() throws IOException {
        Path emptyFile = tempDir.resolve("empty.log");
        Files.createFile(emptyFile);

    List<String> result = LogAnalyzer.solve(emptyFile.toString());

    assertTrue(result.isEmpty());
}



}