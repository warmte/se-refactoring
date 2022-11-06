package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.function.Function;

public class DAO {
    private static final String PATH = "jdbc:sqlite:test.db";

    private static void executeUpdate(FunctionWithException<Connection, PreparedStatement, SQLException> fun) throws SQLException {
        try (Connection c = DriverManager.getConnection(PATH)) {
            PreparedStatement stmt = fun.apply(c);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    private static ArrayList<Product> parseProducts(ResultSet rs) throws SQLException {
        ArrayList<Product> result = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            result.add(new Product(name, price));
        }
        return result;
    }

    private static ArrayList<Product> executeProductQuery(FunctionWithException<Connection, PreparedStatement, SQLException> fun) throws SQLException {
        try (Connection c = DriverManager.getConnection(PATH)) {
            PreparedStatement stmt = fun.apply(c);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Product> products = parseProducts(rs);
            rs.close();
            stmt.close();
            return products;
        }
    }

    private static int executeIntegerQuery(FunctionWithException<Connection, PreparedStatement, SQLException> fun) throws SQLException {
        try (Connection c = DriverManager.getConnection(PATH)) {
            PreparedStatement stmt = fun.apply(c);
            ResultSet rs = stmt.executeQuery();
            int result = 0;
            if (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            return result;
        }
    }

    public static void createProductsTable() throws SQLException {
        executeUpdate(Statements::createTable);
    }

    public static void addProduct(Product product) throws SQLException {
        executeUpdate(c -> Statements.insertProduct(c, product.name, product.price));
    }

    public static void deleteAllProducts() throws SQLException {
        executeUpdate(Statements::deleteAllProducts);
    }

    public static ArrayList<Product> selectAllProducts() throws SQLException {
        return executeProductQuery(Statements::selectAllProducts);
    }

    public static Product selectMin() throws SQLException {
        return executeProductQuery(Statements::selectMin).get(0);
    }

    public static Product selectMax() throws SQLException {
        return executeProductQuery(Statements::selectMax).get(0);
    }

    public static int selectSum() throws SQLException {
        return executeIntegerQuery(Statements::selectSum);
    }

    public static int selectCount() throws SQLException {
        return executeIntegerQuery(Statements::selectCount);
    }


}
