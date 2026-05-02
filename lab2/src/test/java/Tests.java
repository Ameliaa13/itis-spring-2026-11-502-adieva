import org.example.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {
    @Test
    void testStartMethod() {
        Service proxy = ProxyFactory.createProxy(new RealService(), Service.class);
        String result = proxy.start("Hello");
        assertEquals("Hello", result);
    }

    @Test
    void testCalculateMethod() {
        Service proxy = ProxyFactory.createProxy(new RealService(), Service.class);
        int result = proxy.calculate(10, 20);
        assertEquals(30, result);
    }

    @Test
    void testProxyImplementsInterface() {
        Service real = new RealService();
        Service proxy = ProxyFactory.createProxy(real, Service.class);

        assertTrue(proxy instanceof Service);
        assertNotSame(real, proxy);
    }

    @Test
    void testMultipleCalls() {
        Service proxy = ProxyFactory.createProxy(new RealService(), Service.class);

        assertEquals("A", proxy.start("A"));
        assertEquals("B", proxy.start("B"));
        assertEquals(5, proxy.calculate(2, 3));
        assertEquals(100, proxy.calculate(50, 50));
    }
}
