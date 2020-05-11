package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void addDailyReport(Long earnings, Long soldCars) {
        DailyReport dailyReport = new DailyReport();
        dailyReport.setEarnings(earnings);
        dailyReport.setSoldCars(soldCars);

        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public DailyReport getLastDailyReport() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM DailyReport dr ORDER BY dr.id DESC");
        query.setMaxResults(1);
        DailyReport report = (DailyReport) query.uniqueResult();
        transaction.commit();
        session.close();
        return report;
    }

    public void deleteAll() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM DailyReport");
        query.executeUpdate();
        query = session.createQuery("DELETE FROM Car");
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
