package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CarDao {

    private Session session;

    public List<Car> getAllCars() {
        List<Car> cars = session.createQuery("FROM Car").list();
        session.close();
        return cars;
    }

    public Car getCar(String brand, String model, String licensePlate) {
        Query query = session.createQuery("FROM Car WHERE brand = :brand AND model = :model AND licensePlate = :licensePlate");
        query.setParameter("brand", brand);
        query.setParameter("model", model);
        query.setParameter("licensePlate", licensePlate);
        query.setMaxResults(1);
        Car car = (Car) query.uniqueResult();
        session.close();
        return car;
    }

    public int removeCar(Long id) {
        Query query = session.createQuery("DELETE Car WHERE id = :id");
        query.setParameter("id", id);
        int n = query.executeUpdate();
        session.close();
        return n;
    }

    public void addCar(String brand, String model, String licensePlate, Long price) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setLicensePlate(licensePlate);
        car.setPrice(price);

        session.save(car);
        session.close();
    }

    public List<Car> getCars(String brand) {
        Query query = session.createQuery("FROM Car WHERE brand = :brand");
        query.setParameter("brand", brand);
        List<Car> cars = query.list();
        session.close();
        return cars;
    }

    public CarDao setSession(Session session) {
        this.session = session;
        return this;
    }
}
