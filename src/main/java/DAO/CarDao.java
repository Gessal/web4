package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getAllCars() {
        Transaction transaction = session.beginTransaction();
        List<Car> cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return cars;
    }

    public Car getCar(String brand, String model, String licensePlate) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car WHERE brand = :brand AND model = :model AND licensePlate = :licensePlate");
        query.setParameter("brand", brand);
        query.setParameter("model", model);
        query.setParameter("licensePlate", licensePlate);
        query.setMaxResults(1);
        Car car = (Car) query.uniqueResult();
        transaction.commit();
        session.close();
        return car;
    }

    public int removeCar(Long id) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE Car WHERE id = :id");
        query.setParameter("id", id);
        int n = query.executeUpdate();
        transaction.commit();
        session.close();
        return n;
    }

    public void addCar(String brand, String model, String licensePlate, Long price) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setLicensePlate(licensePlate);
        car.setPrice(price);

        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }

    public List<Car> getCars(String brand) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Car WHERE brand = :brand");
        query.setParameter("brand", brand);
        List<Car> cars = query.list();
        transaction.commit();
        session.close();
        return cars;
    }
}
