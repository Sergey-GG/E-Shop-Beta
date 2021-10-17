package vntu.fcsa.gonchar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilkProduct extends Product {
    int id = super.getId();
    String name = super.getName();
    double weight = super.getWeight();
    double price = super.getPrice();

}

