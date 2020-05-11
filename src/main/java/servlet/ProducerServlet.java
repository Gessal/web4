package servlet;

import DAO.DailyReportDao;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        try {
            Long price = Long.parseLong(req.getParameter("price"));
            if (CarService.getInstance().addCar(brand, model, licensePlate, price)) {
                resp.setStatus(200);
            } else {
                resp.setStatus(403);
            }
        } catch (Exception e) {
            resp.setStatus(403);
        }
    }
}
