package ru.akirakozov.sd.refactoring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Statements {
    public static PreparedStatement createTable(Connection c) throws SQLException {
        return c.prepareStatement(
                "CREATE TABLE IF NOT EXISTS PRODUCT (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "NAME TEXT NOT NULL, " +
                        "PRICE INT NOT NULL)"
        );
    }

    public static PreparedStatement insertProduct(Connection c, String name, int price) throws SQLException {
        PreparedStatement stmt = c.prepareStatement("INSERT INTO PRODUCT (NAME, PRICE) VALUES (?, ?)");
        stmt.setString(1, name);
        stmt.setInt(2, price);
        return stmt;
    }

    public static PreparedStatement deleteAllProducts(Connection c) throws SQLException {
        return c.prepareStatement("DELETE FROM PRODUCT");
    }

    public static PreparedStatement selectAllProducts(Connection c) throws SQLException {
        return c.prepareStatement("SELECT * FROM PRODUCT");
    }

    public static PreparedStatement selectMin(Connection c) throws SQLException {
        return c.prepareStatement("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public static PreparedStatement selectMax(Connection c) throws SQLException {
        return c.prepareStatement("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }
    public static PreparedStatement selectSum(Connection c) throws SQLException {
        return c.prepareStatement("SELECT SUM(price) FROM PRODUCT");
    }

    public static PreparedStatement selectCount(Connection c) throws SQLException {
        return c.prepareStatement("SELECT COUNT(*) FROM PRODUCT");
    }
}
