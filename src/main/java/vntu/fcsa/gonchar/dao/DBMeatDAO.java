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
public class DBMeatDAO extends DAOChanger {
    static private int PRODUCTS_COUNT;
    Product meatProduct;
    PreparedStatement preparedStatement;

    @Override
    public List<Product> index() {
        List<Product> meatProducts = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM MeatProduct");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                meatProduct = new MeatProduct();
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
            preparedStatement = connection.prepareStatement("INSERT INTO MeatProduct VALUES(?, ?,?,?)");
            meatProduct.setId(++PRODUCTS_COUNT);
            preparedStatement.setInt(1, meatProduct.getId());
            preparedStatement.setString(2, meatProduct.getName());
            preparedStatement.setDouble(3, meatProduct.getPrice());
            preparedStatement.setDouble(4, meatProduct.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Product show(int id) {
        meatProduct = null;
        try {
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM MeatProduct WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

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
            preparedStatement
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
            preparedStatement =
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
        try {
            show(id);
            preparedStatement
                    = connection.prepareStatement("UPDATE MeatProduct SET weight =? WHERE id = ?");
            preparedStatement.setInt(2, id);
            product.setName(meatProduct.getName());
            product.setPrice(meatProduct.getPrice());
            preparedStatement.setDouble(1, meatProduct.getWeight() - product.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
