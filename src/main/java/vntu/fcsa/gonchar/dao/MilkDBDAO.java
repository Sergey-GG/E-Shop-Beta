package vntu.fcsa.gonchar.dao;

import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.model.MilkProduct;
import vntu.fcsa.gonchar.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MilkDBDAO extends DAOChanger {
    static private int PRODUCTS_COUNT;
    Product milkProduct;
    PreparedStatement preparedStatement;

    @Override
    public List<Product> index() {
        List<Product> milkProducts = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM MilkProduct");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product milkProduct = new MilkProduct();
                milkProduct.setId(resultSet.getInt("id"));
                milkProduct.setName(resultSet.getString("name"));
                milkProduct.setWeight(resultSet.getDouble("weight"));
                milkProduct.setPrice(resultSet.getDouble("price"));

                milkProducts.add(milkProduct);
                ++PRODUCTS_COUNT;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        milkProducts.sort(Comparator.comparing(Product::getId));
        return milkProducts;
    }

    @Override
    public Product show(int id) {
        milkProduct = null;
        try {
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM MilkProduct WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            milkProduct = new MilkProduct();

            milkProduct.setId(resultSet.getInt("id"));
            milkProduct.setName(resultSet.getString("name"));
            milkProduct.setWeight(resultSet.getDouble("weight"));
            milkProduct.setPrice(resultSet.getDouble("price"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return milkProduct;
    }

    @Override
    public void save(MilkProduct milkProduct) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO MilkProduct VALUES(?, ?,?,?)");
            preparedStatement.setInt(1, ++PRODUCTS_COUNT);
            preparedStatement.setString(2, milkProduct.getName());
            preparedStatement.setDouble(3, milkProduct.getPrice());
            preparedStatement.setDouble(4, milkProduct.getWeight());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public void update(int id, Product product) {
        try {
            preparedStatement
                    = connection.prepareStatement("UPDATE MilkProduct SET name=?, weight =?, price=? WHERE id = ?");

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getWeight());
            preparedStatement.setDouble(3, product.getPrice());

            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            preparedStatement =
                    connection.prepareStatement("DELETE FROM MilkProduct WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            --PRODUCTS_COUNT;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void buy(int id, Product product) {
        try {
            show(id);
            preparedStatement
                    = connection.prepareStatement("UPDATE MilkProduct SET weight =? WHERE id = ?");
            preparedStatement.setInt(2, id);
            product.setName(milkProduct.getName());
            product.setPrice(milkProduct.getPrice());
            preparedStatement.setDouble(1, milkProduct.getWeight() - product.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

