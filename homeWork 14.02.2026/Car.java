import java.util.Comparator;
import java.util.Objects;

public class Car {

    String model;
    int year;
    int engineHP;


    public Car(String model, int year, int engineHP) {
        this.model = model;
        this.year = year;
        this.engineHP = engineHP;
    }

    public void setModel(String newModel) {
        this.model = newModel;
    }

    public void setYear(int newYear) {
        this.year = newYear;
    }

    public void setEngineHP(int newEngineHP) {
        this.engineHP = newEngineHP;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getEngineHP() {
        return engineHP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year && Objects.equals(model, car.model) && car.engineHP == engineHP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, year, engineHP);
    }

    @Override
    public String toString() {
        return String.format("\"%s\" %d г. вып. %d л.с.", model, year, engineHP);
    }

}

class CarByModelComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return car1.getModel().compareTo(car2.getModel());
    }
}

class CarByEngineHPComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return Integer.compare(car1.getEngineHP(), car2.getEngineHP());
    }
}

class CarByYearComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return Integer.compare(car1.getYear(), car2.getYear());
    }
}