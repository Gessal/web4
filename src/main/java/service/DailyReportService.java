package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DailyReportService {

    private static DailyReportService dailyReportService;
    private static AtomicLong earning = new AtomicLong(0);
    private static AtomicLong soldCars = new AtomicLong(0);

    private DailyReportDao dailyReportDao;

    private DailyReportService(SessionFactory sessionFactory) {
        this.dailyReportDao = new DailyReportDao(sessionFactory);
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return dailyReportDao.getAllDailyReport();
    }
    public DailyReport getLastReport() {
        return dailyReportDao.getLastDailyReport();
    }

    public void addCurrentReport() {
        dailyReportDao.addDailyReport(earning.get(), soldCars.get());
    }

    public Long addEarning(Long delta) {
        return earning.addAndGet(delta);
    }

    public Long incrementSoldCars() {
        return soldCars.incrementAndGet();
    }

    public void newDay() {
        earning.set(0);
        soldCars.set(0);
    }

    public void deleteAll() {
        dailyReportDao.deleteAll();
    }
}