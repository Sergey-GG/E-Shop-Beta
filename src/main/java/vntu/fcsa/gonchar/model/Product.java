package vntu.fcsa.gonchar.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Product {
    int id;
    @NotEmpty(message = "Name shouldn`t be empty")
    @Size(min = 2, max = 30, message = "Name shouldn`t be too short or long")
    String name;

    @NotEmpty(message = "Weight shouldn`t be empty")
    @Min(value = 0, message = "Weight should be greater than 0")
    double weight;

    @NotEmpty(message = "Cost shouldn`t be empty")
    @Min(value = 0, message = "Cost should be greater than 0")
    double price;

    public String toProductsTXT() {
        return id + ";" + name + ";" + weight + ";" + price + "\n";
    }

}
