package vntu.fcsa.gonchar.dao;

import vntu.fcsa.gonchar.model.MeatProduct;
import vntu.fcsa.gonchar.model.Product;
import vntu.fcsa.gonchar.model.MilkProduct;

import java.util.List;


public interface IProductDAO {
    List<Product> index();

    Product show(int id);

    void save(MilkProduct milkProduct);

    void save(MeatProduct meatProduct);

    void update(int id, Product product);

    void delete(int id);

    void buy(int id, Product product);
}
