package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductServletTest {

    private final HttpServlet servlet = new AddProductServlet();

    @Before
    public void before() throws Exception {
        DAO.init();
    }

    @After
    public void after() throws Exception {
        DAO.clear();
    }

    @Test
    public void baseTest() throws IOException, ServletException {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        when(requestMock.getParameter("name")).thenReturn("1");
        when(requestMock.getParameter("price")).thenReturn("1");
        when(requestMock.getMethod()).thenReturn("GET");
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        when(responseMock.getWriter()).thenReturn(new MockWriter());

        servlet.service(requestMock, responseMock);

        assertEquals("OK" + System.lineSeparator(), responseMock.getWriter().toString());
    }
}
