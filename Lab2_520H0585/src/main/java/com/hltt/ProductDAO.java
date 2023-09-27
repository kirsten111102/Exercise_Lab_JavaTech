package com.hltt;

import java.sql.*;
import java.util.*;

public class ProductDAO implements Repository<Product, Integer> {
    private Connection dbConnection;
    public ProductDAO(String url) throws SQLException {
        dbConnection = DriverManager.getConnection(url);
    }

    public void createDatabase(){
        try(Statement stmt = dbConnection.createStatement();) {
            String sql = "create database ProductManagement";
            stmt.executeUpdate(sql);
            dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
            System.out.println("Database created successfully...");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Database already created");
        }
    }

    public void createTable() throws SQLException{
        dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
        try(Statement stmt = dbConnection.createStatement();) {
            String sql = "create table PRODUCTS(id int primary key auto_increment, name nvarchar(30), price decimal(10, 5), color nvarchar(15), remaining int)";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully...");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Table already created");
        }
    }

    public void deleteTable() throws SQLException{
        dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
        try(Statement stmt = dbConnection.createStatement();) {
            String sql = "drop table PRODUCTS";
            stmt.executeUpdate(sql);
            System.out.println("Table deleted successfully...");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Table doesn't exist");
        }
    }

    public boolean tableExists() throws SQLException{
        dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
        PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT count(*) "
        + "FROM information_schema.tables "
        + "WHERE table_name = ?"
        + "LIMIT 1;");
        preparedStatement.setString(1, "PRODUCTS");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    @Override
    public Integer add(Product item) {
        try {
            String sql = "insert into products(name, price, color, remaining) values(?, ?, ?, ?)";
            Integer Product_id = 0;
            dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
            PreparedStatement pStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pStatement.setString(1, item.getName());
            pStatement.setDouble(2, item.getPrice());
            pStatement.setString(3, item.getColor());
            pStatement.setInt(4, item.getRemaining());

            int rows = pStatement.executeUpdate();
            
            if(rows == 1){
                ResultSet rs = pStatement.getGeneratedKeys();
                if(rs.next()){
                    Product_id = rs.getInt(1);
                }
            }

            return Product_id;
        } catch (SQLException e) {
            return -1;
        }
        
    }

    @Override
    public List<Product> readAll() {
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
            Statement statement = (Statement) dbConnection.createStatement();
            String sql = "select * from products";
            List<Product> products = new ArrayList<>();
            ResultSet dataSet = statement.executeQuery(sql);
            
            while (dataSet.next()) {
                int id = dataSet.getInt(1);
                String name = dataSet.getString(2);
                double price = dataSet.getDouble(3);
                String color = dataSet.getString(4);
                int remaining = dataSet.getInt(5);

                Product p = new Product(id, name, price, color, remaining);
                products.add(p);
            }

            return products;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Product read(Integer id) {
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
            Statement statement = (Statement) dbConnection.createStatement();
            List<Product> products = new ArrayList<>();
            String sql = "select * from products";
            ResultSet dataSet = statement.executeQuery(sql);

            while (dataSet.next()) {
                int prod_id = dataSet.getInt(1);
                String name = dataSet.getString(2);
                double price = dataSet.getDouble(3);
                String color = dataSet.getString(4);
                int remaining = dataSet.getInt(5);

                Product p = new Product(prod_id, name, price, color, remaining);
                products.add(p);
            }
            return products.stream().filter(product->id.equals(product.getId())).findAny().orElse(null);
        } catch (SQLException e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public boolean update(Product item) {
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
            if(delete(item.getId())){
                add(item);
            }
            else return false;
            return true;
        } catch (SQLException e) {
            // TODO: handle exception
            return false;
        }
        
    }

    @Override
    public boolean delete(Integer id) {
        try {
            String sql = "delete from Products where id = ?";
            dbConnection = DriverManager.getConnection("jdbc:mysql://root:@localhost/ProductManagement?useSSL=false");
            PreparedStatement pStatement = (PreparedStatement) dbConnection.prepareStatement(sql);

            pStatement.setInt(1, id);

            pStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
