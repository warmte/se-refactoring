package ru.akirakozov.sd.refactoring.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {
    static void init() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            PreparedStatement stmt = c.prepareStatement("CREATE TABLE IF NOT EXISTS PRODUCT (NAME VARCHAR(100), PRICE INT)");
            stmt.executeUpdate();
            stmt.close();
        }
    }

    static void addProduct(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO PRODUCT (NAME, PRICE) VALUES (?, ?)");
            stmt.setString(1, product.name);
            stmt.setInt(2, product.price);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    static void clear() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            PreparedStatement stmt = c.prepareStatement("DELETE FROM PRODUCT");
            stmt.executeUpdate();
            stmt.close();
        }
    }
}