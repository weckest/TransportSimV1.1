package ecs.components;

import java.util.HashMap;
import java.util.Map;

public class SellOrder implements Component {
    public Map<String, Integer> sell = new HashMap<>();
    public double price;
    public int age;

    public String toString() {
        String output = "SellOrder: {Selling: {";
        for (Map.Entry<String, Integer> entry : sell.entrySet()) {
            String p = entry.getKey();
            Integer i = entry.getValue();
            output += p + ": " + i + ", ";
        }
        output += "}, Price: $" + price + ", Age: " + age + "}";
        return output;
    }
}
