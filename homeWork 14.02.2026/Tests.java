import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void testHumanEqualsAndHashCode() {
        Human p1 = new Human("Amelia", 22);
        Human p2 = new Human("Amelia", 22);
        Human p3 = new Human("Fedor", 30);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void testHumanCompareTo() {
        Human p1 = new Human("Eugene", 30);
        Human p2 = new Human("Amelia", 25);
        Human p3 = new Human("Fedor", 30);

        assertTrue(p1.compareTo(p2) > 0);
        assertTrue(p2.compareTo(p1) < 0);
        assertEquals(0, p1.compareTo(p3));
    }

    @Test
    public void testHumanTreeSetOrder() {
        TreeSet<Human> set = new TreeSet<>();
        set.add(new Human("Eugene", 30));
        set.add(new Human("Amelia", 25));
        set.add(new Human("Fedor", 35));
        set.add(new Human("Eugene", 30));

        assertEquals(3, set.size());
        Human[] array = set.toArray(new Human[0]);
        assertEquals(25, array[0].getAge());
        assertEquals(30, array[1].getAge());
        assertEquals(35, array[2].getAge());
    }

    @Test
    public void testCarEqualsAndHashCode() {
        Car b1 = new Car("BMW Seres 7", 2025, 400);
        Car b2 = new Car("BMW Seres 7", 2025, 400);
        Car b3 = new Car("Lada Granta", 2016, 150);

        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1.hashCode(), b3.hashCode());
    }

    @Test
    public void testCarByYearComparator() {
        Car b1 = new Car("BMW Seres 7", 2020, 400);
        Car b2 = new Car("Lamborghini Huracan", 2025, 1200);
        Car b3 = new Car("Lada Granta", 2077, 150);

        Comparator<Car> comp = new CarByYearComparator();
        assertTrue(comp.compare(b1, b2) < 0);
        assertTrue(comp.compare(b2, b1) > 0);
        assertTrue(comp.compare(b1, b3) < 0);
        assertEquals(0, comp.compare(b1, new Car("TEST", 2020, 100)));
    }

    @Test
    public void testCarByEngineHPComparator() {
        Car b1 = new Car("BMW Seres 7", 2025, 400);
        Car b2 = new Car("Lamborghini Huracan", 2020, 700);
        Car b3 = new Car("Lada Granta", 2077, 1200);

        Comparator<Car> comp = new CarByEngineHPComparator();
        assertTrue(comp.compare(b1, b2) < 0);
        assertTrue(comp.compare(b2, b1) > 0);
        assertTrue(comp.compare(b1, b3) < 0);
        assertEquals(0, comp.compare(b1, new Car("TEST", 2025, 400)));
    }


    @Test
    public void testCarByModelComparator() {
        Car b1 = new Car("BMW Seres 7", 2025, 400);
        Car b2 = new Car("Lamborghini Huracan", 2020, 1200);
        Car b3 = new Car("Lada Granta", 2016, 150);
        Comparator<Car> comp = new CarByModelComparator();
        assertTrue(comp.compare(b1, b3) < 0);  //
        assertTrue(comp.compare(b2, b3) > 0);
        assertEquals(0, comp.compare(b1, new Car("BMW Seres 7", 2020, 150)));
    }

    @Test
    public void testCarTreeSetWithComparator() {
        TreeSet<Car> setByYear = new TreeSet<>(new CarByYearComparator());
        setByYear.add(new Car("BMW Seres 7", 2020, 400));
        setByYear.add(new Car("Lamborghini Huracan", 2025, 700));
        setByYear.add(new Car("Lada Granta", 2077, 1200));
        setByYear.add(new Car("Lamborghini Huracan", 2025, 700));

        assertEquals(3, setByYear.size());
        Car[] array = setByYear.toArray(new Car[0]);
        assertEquals(2020, array[0].getYear());
        assertEquals(2025, array[1].getYear());
        assertEquals(2077, array[2].getYear());

        TreeSet<Car> setByEngineHP = new TreeSet<>(new CarByEngineHPComparator());
        setByEngineHP.add(new Car("BMW Seres 7", 2020, 400));
        setByEngineHP.add(new Car("Lamborghini Huracan", 2025, 700));
        setByEngineHP.add(new Car("Lada Granta", 2077, 1200));
        setByEngineHP.add(new Car("Lamborghini Huracan", 2025, 700));

        assertEquals(3, setByEngineHP.size());
        Car[] array1 = setByEngineHP.toArray(new Car[0]);
        assertEquals(400, array1[0].getEngineHP());
        assertEquals(700, array1[1].getEngineHP());
        assertEquals(1200, array1[2].getEngineHP());

        TreeSet<Car> setByModel = new TreeSet<>(new CarByModelComparator());
        setByModel.add(new Car("BMW Seres 7", 2020, 400));
        setByModel.add(new Car("Lamborghini Huracan", 2016, 700));
        setByModel.add(new Car("Lada Granta", 2077, 1200));

        array1 = setByModel.toArray(new Car[0]);
        assertEquals("BMW Seres 7", array1[0].getModel());
        assertEquals("Lada Granta", array1[1].getModel());
        assertEquals("Lamborghini Huracan", array1[2].getModel());
    }
}
