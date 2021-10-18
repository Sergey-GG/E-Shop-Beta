package vntu.fcsa.gonchar.dao;

import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.model.MeatProduct;
import vntu.fcsa.gonchar.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MeatDBDAO extends DAOChanger {
    static private int PRODUCTS_COUNT;

    @Override
    public List<Product> index() {
        List<Product> meatProducts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM MeatProduct");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product meatProduct = new MeatProduct();
                meatProduct.setId(resultSet.getInt("id"));
                meatProduct.setName(resultSet.getString("name"));
                meatProduct.setWeight(resultSet.getDouble("weight"));
                meatProduct.setPrice(resultSet.getDouble("price"));

                meatProducts.add(meatProduct);
                ++PRODUCTS_COUNT;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        meatProducts.sort(Comparator.comparing(Product::getId));
        return meatProducts;
    }

    @Override
    public void save(MeatProduct meatProduct) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MeatProduct VALUES(?, ?,?,?)");
            meatProduct.setId(++PRODUCTS_COUNT);
            statement.setInt(1, meatProduct.getId());
            statement.setString(2, meatProduct.getName());
            statement.setDouble(3, meatProduct.getPrice());
            statement.setDouble(4, meatProduct.getWeight());
                        statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Product show(int id) {
        Product meatProduct = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM MeatProduct WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            meatProduct = new MeatProduct();
            meatProduct.setId(resultSet.getInt("id"));
            meatProduct.setName(resultSet.getString("name"));
            meatProduct.setPrice(resultSet.getDouble("price"));
            meatProduct.setWeight(resultSet.getDouble("weight"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meatProduct;
    }

    @Override
    public void update(int id, Product product) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("UPDATE MeatProduct SET name=?, weight =?, price=? WHERE id = ?");

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
                    connection.prepareStatement("DELETE FROM MeatProduct WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            --PRODUCTS_COUNT;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void buy(int id, Product product) {
        Product meatProduct;


        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM MeatProduct WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            meatProduct = new MeatProduct();

            meatProduct.setId(resultSet.getInt("id"));
            meatProduct.setName(resultSet.getString("name"));
            meatProduct.setPrice(resultSet.getDouble("price"));
            meatProduct.setWeight(resultSet.getDouble("weight"));
            statement
                    = connection.prepareStatement("UPDATE MeatProduct SET weight =? WHERE id = ?");
            statement.setInt(2, id);
            product.setName(meatProduct.getName());
            product.setPrice(meatProduct.getPrice());
            statement.setDouble(1, meatProduct.getWeight() - product.getWeight());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
