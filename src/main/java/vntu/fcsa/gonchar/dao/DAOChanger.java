package vntu.fcsa.gonchar.dao;

import vntu.fcsa.gonchar.model.MeatProduct;
import vntu.fcsa.gonchar.model.MilkProduct;
import vntu.fcsa.gonchar.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DAOChanger implements IProductDAO {
//    @Value("${dAOChanger.URL}")
//    static String dbUrl;
    static final String URL= "jdbc:postgresql://localhost:5432/e_shop_db";

//    @Value("${dAOChanger.USERNAME}")
//    static String dbUsername;
    static final String USERNAME = "postgres";

//    @Value("${dAOChanger.PASSWORD}")
//    static String dbPassword;
    static final String PASSWORD= "eshop";

    static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Product> index() {
        return null;
    }

    @Override
    public Product show(int id) {
        return null;
    }

    @Override
    public void save(MilkProduct milkProduct) {
    }

    @Override
    public void save(MeatProduct meatProduct) {
    }

    @Override
    public void update(int id, Product product) {
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public void buy(int id, Product product) {
    }
}
