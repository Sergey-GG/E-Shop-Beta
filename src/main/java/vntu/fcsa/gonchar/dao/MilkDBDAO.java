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
    private int PRODUCTS_COUNT;

    @Override
    public List<Product> index() {
        List<Product> milkProducts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM MilkProduct");
            ResultSet resultSet = statement.executeQuery();

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
        Product milkProduct = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM MilkProduct WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement("?,?,?,?");
            statement.setInt(1, ++PRODUCTS_COUNT);
            statement.setString(2, milkProduct.getName());
            statement.setDouble(3, milkProduct.getPrice());
            statement.setDouble(4, milkProduct.getWeight());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public void update(int id, Product product) {
        try {
            PreparedStatement preparedStatement
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
            PreparedStatement preparedStatement =
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
        Product milkProduct;


        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM MeatProduct WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            milkProduct = new MilkProduct();

            milkProduct.setId(resultSet.getInt("id"));
            milkProduct.setName(resultSet.getString("name"));
            milkProduct.setPrice(resultSet.getDouble("price"));
            milkProduct.setWeight(resultSet.getDouble("weight"));
            statement
                    = connection.prepareStatement("UPDATE MilkProduct SET weight =? WHERE id = ?");
            statement.setInt(2, id);
            product.setName(milkProduct.getName());
            product.setPrice(milkProduct.getPrice());
            statement.setDouble(1, milkProduct.getWeight() - product.getWeight());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

