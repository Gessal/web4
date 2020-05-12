package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        session.close();
        return dailyReports;
    }

    public void addDailyReport(Long earnings, Long soldCars) {
        DailyReport dailyReport = new DailyReport();
        dailyReport.setEarnings(earnings);
        dailyReport.setSoldCars(soldCars);

        session.save(dailyReport);
        session.close();
    }

    public DailyReport getLastDailyReport() {
        Query query = session.createQuery("FROM DailyReport dr ORDER BY dr.id DESC");
        query.setMaxResults(1);
        DailyReport report = (DailyReport) query.uniqueResult();
        session.close();
        return report;
    }

    public void deleteAll() {
        Query query = session.createQuery("DELETE FROM DailyReport");
        query.executeUpdate();
        query = session.createQuery("DELETE FROM Car");
        query.executeUpdate();
        session.close();
    }

    public DailyReportDao setSession(Session session) {
        this.session = session;
        return this;
    }
}
