package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.DAO;
import ru.akirakozov.sd.refactoring.html.HTMLBuilder;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                Product product = DAO.selectMax();
                response.getWriter().print(HTMLBuilder.bodyView(HTMLBuilder.maxPriceDescription + HTMLBuilder.singleProductView(product)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                Product product = DAO.selectMin();
                response.getWriter().print(HTMLBuilder.bodyView(HTMLBuilder.minPriceDescription + HTMLBuilder.singleProductView(product)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                int sum = DAO.selectSum();
                response.getWriter().print(HTMLBuilder.bodyView(HTMLBuilder.sumPriceDescription + sum));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                int count = DAO.selectCount();
                response.getWriter().print(HTMLBuilder.bodyView(HTMLBuilder.productsCountedDescription + count));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
