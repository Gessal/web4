package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public Car buyCar(String brand, String model, String licensePlate) {
        Car car = new CarDao(sessionFactory.openSession()).getCar(brand, model, licensePlate);
        if (car != null) {
            new CarDao(sessionFactory.openSession()).removeCar(car.getId());
            DailyReportService.getInstance().addEarning(car.getPrice());
            DailyReportService.getInstance().incrementSoldCars();
        }
        return car;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        if (new CarDao(sessionFactory.openSession()).getCars(brand).size() < 10) {
            new CarDao(sessionFactory.openSession()).addCar(brand, model, licensePlate, price);
            return true;
        }
        return false;
    }
}