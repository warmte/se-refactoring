package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;

import javax.servlet.http.HttpServlet;

public abstract class ServletTest {

    protected HttpServlet servlet;

    protected abstract void setServlet();

    @Before
    public void before() throws Exception {
        DAO.init();
        setServlet();
    }

    @After
    public void after() throws Exception {
        DAO.clear();
    }
}
