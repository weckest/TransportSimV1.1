package ecs.components;

import java.util.HashMap;
import java.util.Map;

public class BuyOrder implements Component {
    public Map<String, Integer> buy = new HashMap<>();
    public double price = 0.0;
    public int age = 0;

    public String toString() {
        String output = "BuyOrder: {Want: {";
        for (Map.Entry<String, Integer> entry : buy.entrySet()) {
            String p = entry.getKey();
            Integer i = entry.getValue();
            output += p + ": " + i + ", ";
        }
        output += "}, Price: $" + price + ", Age: " + age + "}";
        return output;
    }
}
