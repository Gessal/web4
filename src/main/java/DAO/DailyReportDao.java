package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DailyReportDao {

    private SessionFactory sessionFactory;

    public DailyReportDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<DailyReport> getAllDailyReport() {
        Session session = sessionFactory.openSession();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        session.close();
        return dailyReports;
    }

    public void addDailyReport(Long earnings, Long soldCars) {
        DailyReport dailyReport = new DailyReport();
        dailyReport.setEarnings(earnings);
        dailyReport.setSoldCars(soldCars);

        Session session = sessionFactory.openSession();
        session.save(dailyReport);
        session.close();
    }

    public DailyReport getLastDailyReport() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM DailyReport dr ORDER BY dr.id DESC");
        query.setMaxResults(1);
        DailyReport report = (DailyReport) query.uniqueResult();
        session.close();
        return report;
    }

    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("DELETE FROM DailyReport");
        query.executeUpdate();
        query = session.createQuery("DELETE FROM Car");
        query.executeUpdate();
        session.close();
    }
}
