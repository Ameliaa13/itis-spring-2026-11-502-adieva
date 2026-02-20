import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        TreeSet<Human> set = new TreeSet<>();
        set.add(new Human("Eugene", 18));
        set.add(new Human("Amelia", 25));
        set.add(new Human("TestName", 70));
        set.add(new Human("SpongeBob", 44));

        for (Human p : set) {
            System.out.println(p);
        }

        TreeSet<Car> setByEngineHP = new TreeSet<>(new CarByEngineHPComparator());
        setByEngineHP.add(new Car("BMW Seres 7", 2025, 400));
        setByEngineHP.add(new Car("Lamborghini Huracan", 2020, 1200));
        setByEngineHP.add(new Car("Lada Granta", 2016, 150));

        System.out.println("EngineHP comparator:");
        for (Car b : setByEngineHP) {
            System.out.println(b);
        }

        TreeSet<Car> setByModel = new TreeSet<>(new CarByModelComparator());
        setByModel.add(new Car("BMW Seres 7", 2025, 400));
        setByModel.add(new Car("Lamborghini Huracan", 2020, 1200));
        setByModel.add(new Car("Lada Granta", 2016, 150));

        System.out.println("Model comparator:");
        for (Car b : setByModel) {
            System.out.println(b);
        }

        TreeSet<Car> setByYear = new TreeSet<>(new CarByYearComparator());
        setByYear.add(new Car("BMW Seres 7", 2025, 400));
        setByYear.add(new Car("Lamborghini Huracan", 2020, 1200));
        setByYear.add(new Car("Lada Granta", 2016, 150));

        System.out.println("Year comparator:");
        for (Car b : setByYear) {
            System.out.println(b);
        }
    }
}