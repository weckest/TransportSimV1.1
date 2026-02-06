package ecs.events;

import java.util.HashMap;
import java.util.Map;

public class SellRequest implements Event {
    public Map<String, Integer> sell = new HashMap<>();

    public String toString() {
        String output = "SellRequest: {";
        for (Map.Entry<String, Integer> entry : sell.entrySet()) {
            String p = entry.getKey();
            Integer i = entry.getValue();
            output += p + ": " + i + ", ";
        }
        output += "}";
        return output;
    }
}
