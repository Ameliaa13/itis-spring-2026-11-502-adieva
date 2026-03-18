import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private List<TransactionEntity> transactions;

    @BeforeEach
    void setUp() {
        transactions = new java.util.ArrayList<>();
    }

    private TransactionEntity createTransaction(long idFrom, long idTo, double amount, boolean success, String denyReason) {
        TransactionEntity t = new TransactionEntity();
        t.setUserIdFrom(idFrom);
        t.setUserIdTo(idTo);
        t.setAmount(amount);
        t.setSuccess(success);
        t.setDenyReason(denyReason);
        return t;
    }

    @Test
    void solve1() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 200.0, false, "error"));
        transactions.add(createTransaction(2, 1, 150.0, true, null));

        double result = MainGet.solve1(transactions);
        assertEquals(200.0, result, 0.001);
    }

    @Test
    void solve1_ReNull() {
        double result = MainGet.solve1(transactions);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    void solve2() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 50.0, true, null));
        transactions.add(createTransaction(2, 1, 200.0, false, "error"));

        double result = MainGet.solve2(transactions);
        assertEquals(50.0, result, 0.001);
    }

    @Test
    void solve2_ReNull() {
        transactions.add(createTransaction(1, 2, 100.0, false, "err1"));
        transactions.add(createTransaction(1, 3, 50.0, false, "err2"));

        double result = MainGet.solve2(transactions);
        assertEquals(0.0, result, 0.001);
    }


    @Test
    void solve3() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 50.0, false, "err"));
        transactions.add(createTransaction(2, 1, 200.0, false, "err"));

        double result = MainGet.solve3(transactions);
        assertEquals(2.0 / 3.0, result, 0.001);
    }

    @Test
    void solve4() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 50.0, false, "err"));
        transactions.add(createTransaction(2, 1, 200.0, true, null));
        transactions.add(createTransaction(3, 1, 150.0, true, null));

        Set<Long> result = MainGet.solve4(transactions);
        assertEquals(Set.of(1L, 2L, 3L), result);
    }

    @Test
    void solve4_ReEmpty() {
        Set<Long> result = MainGet.solve4(transactions);
        assertTrue(result.isEmpty());
    }


    @Test
    void solve5() {
        transactions.add(createTransaction(1, 2, 100.0, false, "err1"));
        transactions.add(createTransaction(1, 3, 50.0, false, "err2"));
        transactions.add(createTransaction(2, 1, 200.0, false, "err1"));
        transactions.add(createTransaction(3, 1, 150.0, true, "null")); // success, denyReason игнорируется? В функции фильтр по !equals("null"), но success не учитывается, но denyReason "null" отфильтруется.

        List<String> result = MainGet.solve5(transactions);
        assertEquals(List.of("err1", "err2"), result);
    }


    @Test
    void solve6() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 50.0, true, null));
        transactions.add(createTransaction(2, 1, 200.0, false, "err"));
        transactions.add(createTransaction(2, 3, 75.0, true, null));

        Map<Long, Double> result = MainGet.solve6(transactions);
        assertEquals(2, result.size());
        assertEquals(150.0, result.get(1L), 0.001);
        assertEquals(75.0, result.get(2L), 0.001);
        assertNull(result.get(3L));
    }

    @Test
    void solve6_ReEmpty() {
        transactions.add(createTransaction(1, 2, 100.0, false, "err"));

        Map<Long, Double> result = MainGet.solve6(transactions);
        assertTrue(result.isEmpty());
    }


    @Test
    void solve7() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(2, 2, 200.0, true, null));
        transactions.add(createTransaction(1, 3, 150.0, true, null));
        transactions.add(createTransaction(3, 3, 300.0, false, "err"));

        Map<Long, Double> result = MainGet.solve7(transactions);
        assertEquals(2, result.size());
        assertEquals((100.0 + 200.0) / 2, result.get(2L), 0.001);
        assertEquals(150.0, result.get(3L), 0.001);
    }

    @Test
    void solve7_ReEmpty() {
        transactions.add(createTransaction(1, 2, 100.0, false, "err"));

        Map<Long, Double> result = MainGet.solve7(transactions);
        assertTrue(result.isEmpty());
    }


    @Test
    void solve8() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(2, 3, 500.0, true, null));
        transactions.add(createTransaction(3, 1, 300.0, true, null));
        transactions.add(createTransaction(4, 5, 400.0, true, null));
        transactions.add(createTransaction(5, 6, 200.0, true, null));
        transactions.add(createTransaction(6, 7, 600.0, true, null));

        List<TransactionEntity> result = MainGet.solve8(transactions);
        assertEquals(5, result.size());
        assertEquals(600.0, result.get(0).getAmount(), 0.001);
        assertEquals(500.0, result.get(1).getAmount(), 0.001);
        assertEquals(400.0, result.get(2).getAmount(), 0.001);
        assertEquals(300.0, result.get(3).getAmount(), 0.001);
        assertEquals(200.0, result.get(4).getAmount(), 0.001);
    }

    @Test
    void solve8_ReAll() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(2, 3, 50.0, true, null));

        List<TransactionEntity> result = MainGet.solve8(transactions);
        assertEquals(2, result.size());
    }


    @Test
    void solve9() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 50.0, true, null));   // sender 1 все успешны
        transactions.add(createTransaction(2, 1, 200.0, false, "err")); // sender 2 есть неуспешная
        transactions.add(createTransaction(2, 4, 75.0, true, null));
        transactions.add(createTransaction(3, 5, 150.0, true, null));   // sender 3 только одна успешная

        List<Long> result = MainGet.solve9(transactions);
        assertTrue(result.contains(1L));
        assertTrue(result.contains(3L));
        assertFalse(result.contains(2L));
    }

    @Test
    void solve9_ReEmpty() {
        transactions.add(createTransaction(1, 2, 100.0, false, "err"));
        transactions.add(createTransaction(1, 3, 50.0, true, null));

        List<Long> result = MainGet.solve9(transactions);
        assertTrue(result.isEmpty());
    }


    @Test
    void solve10() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 50.0, false, "err"));
        transactions.add(createTransaction(2, 1, 200.0, false, "err2"));

        Map<Boolean, List<TransactionEntity>> result = MainGet.solve10(transactions);
        assertEquals(1, result.get(true).size());
        assertEquals(2, result.get(false).size());
    }

    @Test
    void solve10_ReEmpty() {
        Map<Boolean, List<TransactionEntity>> result = MainGet.solve10(transactions);
        assertTrue(result.get(true).isEmpty());
        assertTrue(result.get(false).isEmpty());
    }

    @Test
    void solve11() {

        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 2, 50.0, true, null));
        transactions.add(createTransaction(1, 3, 200.0, true, null));
        transactions.add(createTransaction(2, 1, 75.0, true, null));

        Map<Long, Map<Long, Double>> result = MainGet.solve11(transactions);
        assertEquals(2, result.size());
        assertEquals(150.0, result.get(1L).get(2L), 0.001);
        assertEquals(200.0, result.get(1L).get(3L), 0.001);
        assertEquals(75.0, result.get(2L).get(1L), 0.001);
         }


    @Test
    void solve12() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 2, 50.0, false, "err"));
        transactions.add(createTransaction(1, 3, 200.0, true, null));
        transactions.add(createTransaction(2, 1, 75.0, true, null));
        transactions.add(createTransaction(2, 1, 80.0, true, null));
        transactions.add(createTransaction(2, 1, 90.0, false, "err"));

        Map.Entry<AbstractMap.SimpleEntry<Long, Long>, Long> result = MainGet.solve12(transactions);
        assertEquals(3L, result.getValue().longValue());
        assertEquals(2L, result.getKey().getKey().longValue());
        assertEquals(1L, result.getKey().getValue().longValue());
    }

    @Test
    void solve12_ThrowException() {
        assertThrows(RuntimeException.class, () -> MainGet.solve12(transactions));
    }


    @Test
    void solve13() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 200.0, true, null));
        transactions.add(createTransaction(2, 1, 300.0, true, null));
        // среднее = 200
        List<TransactionEntity> result = MainGet.solve13(transactions);
        assertEquals(1, result.size());
        assertEquals(300.0, result.get(0).getAmount(), 0.001);
    }

    @Test
    void solve13_ReEmpty() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 3, 100.0, true, null));
        List<TransactionEntity> result = MainGet.solve13(transactions);
        assertTrue(result.isEmpty());
    }

    @Test
    void solve13_ReEmpty2() {
        List<TransactionEntity> result = MainGet.solve13(transactions);
        assertTrue(result.isEmpty());
    }

    @Test
    void solve14() {
        for (int i = 0; i < 6; i++) {
            transactions.add(createTransaction(1, 2, 100.0, false, "err1"));
        }
        for (int i = 0; i < 4; i++) {
            transactions.add(createTransaction(1, 3, 50.0, false, "err2"));
        }
        transactions.add(createTransaction(2, 1, 200.0, true, null));

        List<String> result = MainGet.solve14(transactions);
        assertEquals(List.of("err1"), result);
    }

    @Test
    void solve14_ReEmpty() {
        for (int i = 0; i < 3; i++) {
            transactions.add(createTransaction(1, 2, 100.0, false, "err1"));
        }
        List<String> result = MainGet.solve14(transactions);
        assertTrue(result.isEmpty());
    }


    @Test
    void solve15() {
        transactions.add(createTransaction(1, 2, 100.0, true, null));
        transactions.add(createTransaction(1, 2, 200.0, true, null));
        transactions.add(createTransaction(2, 3, 150.0, true, null));
        transactions.add(createTransaction(3, 3, 50.0, false, "err")); // неуспешная не учитывается? В solve15 нет фильтра по успешности, поэтому учитывается.

        long result = MainGet.solve15(transactions);
        assertEquals(2L, result);
    }

    @Test
    void solve15_2() {
        assertThrows(RuntimeException.class, () -> MainGet.solve15(transactions));
    }
}