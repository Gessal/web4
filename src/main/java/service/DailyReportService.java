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

    private SessionFactory sessionFactory;
    private DailyReportDao dailyReportDao;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return dailyReportDao.setSession(sessionFactory.openSession()).getAllDailyReport();
    }
    public DailyReport getLastReport() {
        return dailyReportDao.setSession(sessionFactory.openSession()).getLastDailyReport();
    }

    public void addCurrentReport() {
        dailyReportDao.setSession(sessionFactory.openSession()).addDailyReport(earning.get(), soldCars.get());
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
        dailyReportDao.setSession(sessionFactory.openSession()).deleteAll();
    }
}