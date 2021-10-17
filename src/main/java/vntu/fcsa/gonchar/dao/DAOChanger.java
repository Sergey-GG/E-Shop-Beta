package vntu.fcsa.gonchar.dao;

import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.model.MeatProduct;
import vntu.fcsa.gonchar.model.MilkProduct;
import vntu.fcsa.gonchar.model.Product;

import java.util.List;
@Component
public class DAOChanger implements IProductDAO{
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
