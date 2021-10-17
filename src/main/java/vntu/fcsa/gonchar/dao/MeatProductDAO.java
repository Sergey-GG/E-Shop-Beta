package vntu.fcsa.gonchar.dao;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.model.MeatProduct;
import vntu.fcsa.gonchar.model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class MeatProductDAO extends DAOChanger {
    static final String MEAT_PRODUCTS_TXT = "C:/Users/fsnge/IdeaProjects/E-Shop Beta/meatProducts.txt";
    public List<Product> meatProducts;
    private int PRODUCTS_COUNT;

    {
        meatProducts = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(MEAT_PRODUCTS_TXT), StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                Product product = new MeatProduct();
                String[] strings = scanner.nextLine().split(";");
                product.setId(Integer.parseInt(strings[0]));
                product.setName(strings[1]);
                product.setWeight(Double.parseDouble(strings[2]));
                product.setPrice(Double.parseDouble(strings[3]));
                meatProducts.add(new Product(product.getId(), product.getName(), product.getWeight(),
                        product.getPrice()));
                ++PRODUCTS_COUNT;

            }
            scanner.close();
        } catch (IOException |
                ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
        meatProducts.sort(Comparator.comparing(Product::getId));
    }

    @Override
    public List<Product> index() {
        return meatProducts;
    }

    @Override
    public Product show(int id) {
        return meatProducts.stream().filter(milkProduct -> milkProduct.getId() == id).findAny().orElse(null);
    }

    @Override
    public void save(MeatProduct meatProduct) {
        meatProduct.setId(++PRODUCTS_COUNT);
        meatProducts.add(meatProduct);
        writeMeats();
    }

    @Override
    public void update(int id, @Valid Product updatedProduct) {
        Product productToBeUpdated = show(id);
        productToBeUpdated.setName(updatedProduct.getName());
        productToBeUpdated.setPrice(updatedProduct.getPrice());
        productToBeUpdated.setWeight(updatedProduct.getWeight());
        writeMeats();
    }

    @Override
    public void delete(int id) {
        meatProducts.removeIf(p -> p.getId() == id);
        --PRODUCTS_COUNT;
        writeMeats();
    }

    @Override
    public void buy(int id, @Valid Product celledProduct) {
        Product productToBeUpdated = show(id);
        productToBeUpdated.setWeight(productToBeUpdated.getWeight() - celledProduct.getWeight());
        celledProduct.setName(productToBeUpdated.getName());
        celledProduct.setPrice(productToBeUpdated.getPrice());
        writeMeats();
    }


    void writeMeats() {
        try {
            FileWriter fileWriter = new FileWriter(MEAT_PRODUCTS_TXT);
            meatProducts.sort(Comparator.comparing(Product::getId));
            for (Product meatProduct : meatProducts) {
                fileWriter.write(meatProduct.toProductsTXT());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
