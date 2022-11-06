package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.dao.DAO;
import ru.akirakozov.sd.refactoring.html.HTMLBuilder;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryServletTest {
    private final HttpServlet servlet = new QueryServlet();

    @Before
    public void before() throws Exception {
        DAO.createProductsTable();
    }

    @After
    public void after() throws Exception {
        DAO.deleteAllProducts();
    }

    private void baseTest(String command, List<Product> products) throws SQLException, ServletException, IOException {
        for (Product product : products) {
            DAO.addProduct(product);
        }

        String expectedBody;
        switch (command) {
            case "sum":
                expectedBody = HTMLBuilder.sumPriceDescription + products.stream().map(product -> product.price).reduce(0, Integer::sum);
                break;
            case "count":
                expectedBody = HTMLBuilder.productsCountedDescription + products.size();
                break;
            case "max":
                int value = products.stream().map(product -> product.price).max(Integer::compare).orElse(Integer.MIN_VALUE);
                Product chosen = products.stream().filter(product -> product.price == value).findFirst().orElse(null);
                expectedBody = HTMLBuilder.maxPriceDescription + (chosen != null ? HTMLBuilder.singleProductView(chosen) : "");
                break;
            case "min":
                value = products.stream().map(product -> product.price).min(Integer::compare).orElse(Integer.MAX_VALUE);
                chosen = products.stream().filter(product -> product.price == value).findFirst().orElse(null);
                expectedBody = HTMLBuilder.minPriceDescription + (chosen != null ? HTMLBuilder.singleProductView(chosen) : "");
                break;
            default:
                expectedBody = "";
        }

        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        when(requestMock.getParameter("command")).thenReturn(command);
        when(requestMock.getMethod()).thenReturn("GET");
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        when(responseMock.getWriter()).thenReturn(new MockWriter());

        servlet.service(requestMock, responseMock);

        assertEquals(HTMLBuilder.bodyView(expectedBody), responseMock.getWriter().toString());
    }

    @Test
    public void sumTest() throws ServletException, SQLException, IOException {
        baseTest("sum", List.of(new Product("1", 1), new Product("2", 2), new Product("3", 2)));
    }

    @Test
    public void countTest() throws ServletException, SQLException, IOException {
        baseTest("count", List.of(new Product("1", 1), new Product("2", 2), new Product("3", 2)));
    }

    @Test
    public void maxTest() throws ServletException, SQLException, IOException {
        baseTest("max", List.of(new Product("1", 1), new Product("2", 2), new Product("3", 2)));
    }

    @Test
    public void minTest() throws ServletException, SQLException, IOException {
        baseTest("min", List.of(new Product("1", 1), new Product("2", 2), new Product("3", 2)));
    }
}
