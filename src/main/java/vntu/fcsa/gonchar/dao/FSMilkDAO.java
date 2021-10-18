package vntu.fcsa.gonchar.dao;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import vntu.fcsa.gonchar.model.MilkProduct;
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
public class FSMilkDAO extends DAOChanger {
    static final String MILK_PRODUCTS_TXT = "C:/Users/fsnge/IdeaProjects/E-Shop Beta/milkProducts.txt";
    public List<Product> milkProducts;
    private int PRODUCTS_COUNT;

    {
        milkProducts = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(MILK_PRODUCTS_TXT), StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                Product product = new MilkProduct();
                String[] strings = scanner.nextLine().split(";");
                product.setId(Integer.parseInt(strings[0]));
                product.setName(strings[1]);
                product.setWeight(Double.parseDouble(strings[2]));
                product.setPrice(Double.parseDouble(strings[3]));
                milkProducts.add(new MilkProduct(product.getId(), product.getName(), product.getWeight(),
                        product.getPrice()));
                ++PRODUCTS_COUNT;

            }
            scanner.close();
        } catch (IOException |
                ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
        milkProducts.sort(Comparator.comparing(Product::getId));
    }

    @Override
    public List<Product> index() {
        return milkProducts;
    }

    @Override
    public Product show(int id) {
        return milkProducts.stream().filter(milkProduct -> milkProduct.getId() == id).findAny().orElse(null);
    }

    @Override
    public void save(MilkProduct milkProduct) {
        milkProduct.setId(++PRODUCTS_COUNT);
        milkProducts.add(milkProduct);
        writeMilks();
    }


    @Override
    public void update(int id, @Valid Product updatedProduct) {
        Product productToBeUpdated = show(id);
        productToBeUpdated.setName(updatedProduct.getName());
        productToBeUpdated.setPrice(updatedProduct.getPrice());
        productToBeUpdated.setWeight(updatedProduct.getWeight());
        writeMilks();
    }

    @Override
    public void delete(int id) {
        milkProducts.removeIf(p -> p.getId() == id);
        --PRODUCTS_COUNT;
        writeMilks();
    }

    @Override
    public void buy(int id, @Valid Product celledProduct) {
        Product productToBeUpdated = show(id);
        productToBeUpdated.setWeight(productToBeUpdated.getWeight() - celledProduct.getWeight());
        celledProduct.setName(productToBeUpdated.getName());
        celledProduct.setPrice(productToBeUpdated.getPrice());
        writeMilks();
    }


    void writeMilks() {
        try {
            FileWriter fileWriter = new FileWriter(MILK_PRODUCTS_TXT);
            milkProducts.sort(Comparator.comparing(Product::getId));
            for (Product milkProduct : milkProducts) {
                fileWriter.write(milkProduct.toProductsTXT());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

