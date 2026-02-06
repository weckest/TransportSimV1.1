package ecs.components;

import java.util.HashMap;
import java.util.Map;

public class SellOrder implements Component {
    public Map<String, Integer> sell = new HashMap<>();
    public Map<String, Double> price = new HashMap<>();
    public int age;

    public String toString() {
        String output = "SellOrder: {Selling: {";
        for (Map.Entry<String, Integer> entry : sell.entrySet()) {
            String p = entry.getKey();
            Double pr = price.get(p);
            Integer i = entry.getValue();
            output += p + ": " + i + " @ $" + pr/i + ", ";
        }
        output += "}, Price: $" + price + ", Age: " + age + "}";
        return output;
    }
}
