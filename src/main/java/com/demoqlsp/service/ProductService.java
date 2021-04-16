package com.demoqlsp.service;

import com.demoqlsp.model.Category;
import com.demoqlsp.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String SELECT_ALL_PRODUCTS = "select * from product";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product" + "  (name, price, quantity, color, description, category) VALUES " +
            " (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_CATEGORY_NAME_WHERE_PRODUCT_ID = "select category.name from category, product where category.cateId = product.category and product.id =?";
    private static final String SELECT_ALL_CATEGORY = "select * from category;";
    private static final String SELECT_PRODUCT_BY_ID = "select id,name,price,quantity,color,description,category from product where id =?";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?,price= ?, quantity =?, color = ?, description=?, category=? where id = ?;";
    private static final String DELETE_PRODUCT_SQL = "delete from product where id = ?;";
    private static final String SEARCH_BY_NAME = "select * from product where name like ?;";

    public ProductService() {

    }

    public List<Product> selectAllUsers() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConection.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int category = rs.getInt("category");
                products.add(new Product(id, name, price, quantity, color, description, category));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    private void printSQLException(SQLException e) {
    }

    public void insertUser(Product product) {
        System.out.println(INSERT_PRODUCT_SQL);
        try (Connection connection = DatabaseConection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getCategory());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Category selectCategoryByProductId(int id) {
        Category category = null;
        try (Connection connection = DatabaseConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_NAME_WHERE_PRODUCT_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                category= new Category(name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return category;
    }

    public List<Category> getAllCategory(){
        List<Category> listCategory = null;
        Connection connection = DatabaseConection.getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORY);
                ResultSet resultSet = preparedStatement.executeQuery();
                listCategory = new ArrayList<>();
                while (resultSet.next()) {
                    int categoryId = resultSet.getInt("cateId");
                    String name = resultSet.getString("name");
                    Category category = new Category(categoryId, name);
                    listCategory.add(category);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listCategory;
    }

    public Product selectProduct(int pid) {
        Product product = null;
        try (Connection connection = DatabaseConection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setInt(1, pid);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int category = rs.getInt("category");

                product = new Product(id, name, price, quantity, color, description, category);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    public boolean updateProduct(Product product) {
        boolean rowUpdated = false;
        try (Connection connection = DatabaseConection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL);) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getCategory());
            preparedStatement.setInt(7, product.getId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DatabaseConection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public List<Product> searchByName(String textSearch) {
        List<Product> list = new ArrayList<>();

        try {
            Connection connection = DatabaseConection.getConnection();
            PreparedStatement ps = connection.prepareStatement(SEARCH_BY_NAME);
            ps.setString(1, "%" + textSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int category = rs.getInt("category");
                list.add(new Product(id, name, price, quantity, color, description, category));
            }
        } catch (Exception e) {
        }
        return list;
    }
}
