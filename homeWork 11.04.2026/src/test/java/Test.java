import org.example.Fibonacci;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {

    @Test
    void testFibonacciZero() {
        assertEquals(0, Fibonacci.fibonacci(0));
    }

    @Test
    void testFibonacciOne() {
        assertEquals(1, Fibonacci.fibonacci(1));
    }

    @Test
    void testFibonacciFive() {
        assertEquals(5, Fibonacci.fibonacci(5));
    }

    @Test
    void testFibonacciNegativeThrowsException() {
        assertThrows(RuntimeException.class, () -> Fibonacci.fibonacci(-1));
    }

    @Test
    void testFibonacciParallelBasic() {
        List<Integer> indexes = Arrays.asList(5, 10, 15);
        Map<Integer, Long> result = Fibonacci.fibonacciParallel(indexes, 3);

        assertEquals(3, result.size());
        assertEquals(5L, result.get(5));
        assertEquals(55L, result.get(10));
        assertEquals(610L, result.get(15));
    }
}