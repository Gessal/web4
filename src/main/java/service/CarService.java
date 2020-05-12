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
    private CarDao carDao;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.carDao = new CarDao();
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return carDao.setSession(sessionFactory.openSession()).getAllCars();
    }

    public Car buyCar(String brand, String model, String licensePlate) {
        Car car = carDao.setSession(sessionFactory.openSession()).getCar(brand, model, licensePlate);
        if (car != null) {
            carDao.setSession(sessionFactory.openSession()).removeCar(car.getId());
            DailyReportService.getInstance().addEarning(car.getPrice());
            DailyReportService.getInstance().incrementSoldCars();
        }
        return car;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        if (carDao.setSession(sessionFactory.openSession()).getCars(brand).size() < 10) {
            carDao.setSession(sessionFactory.openSession()).addCar(brand, model, licensePlate, price);
            return true;
        }
        return false;
    }
}