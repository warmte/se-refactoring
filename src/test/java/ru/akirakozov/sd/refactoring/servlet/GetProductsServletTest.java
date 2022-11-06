package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetProductsServletTest {
    String EOL = System.lineSeparator();

    private final HttpServlet servlet = new GetProductsServlet();

    @Before
    public void before() throws Exception {
        DAO.init();
    }

    @After
    public void after() throws Exception {
        DAO.clear();
    }

    private void baseTest(List<Product> products) throws SQLException, ServletException, IOException {
        for (Product product : products) {
            DAO.addProduct(product);
        }

        String productsStr = products.stream().map(p -> p.name + "\t" + p.price + "</br>").collect(Collectors.joining(EOL));
        String expectedBody = String.format("<body>%s%s%s</body>", EOL, productsStr, productsStr.isEmpty() ? "" : EOL);

        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        when(requestMock.getMethod()).thenReturn("GET");
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        when(responseMock.getWriter()).thenReturn(new MockWriter());

        servlet.service(requestMock, responseMock);

        assertEquals(String.format("<html>%s</html>%s", expectedBody, EOL), responseMock.getWriter().toString());
    }

    @Test
    public void emptyTest() throws SQLException, ServletException, IOException {
        baseTest(Collections.emptyList());
    }

    @Test
    public void singletonTest() throws SQLException, ServletException, IOException {
        baseTest(List.of(new Product("1", 1)));
    }

    @Test
    public void collectionTest() throws SQLException, ServletException, IOException {
        baseTest(List.of(new Product("1", 1), new Product("2", 2), new Product("3", 2)));
    }
}
