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

    private CarDao carDao;

    private CarService(SessionFactory sessionFactory) {
        this.carDao = new CarDao(sessionFactory);
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    public Car buyCar(String brand, String model, String licensePlate) {
        Car car = carDao.getCar(brand, model, licensePlate);
        if (car != null) {
            carDao.removeCar(car.getId());
            DailyReportService.getInstance().addEarning(car.getPrice());
            DailyReportService.getInstance().incrementSoldCars();
        }
        return car;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        if (carDao.getCars(brand).size() < 10) {
            carDao.addCar(brand, model, licensePlate, price);
            return true;
        }
        return false;
    }
}