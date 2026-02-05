package ecs.events;

import java.util.HashMap;
import java.util.Map;

public class BuyRequest implements Event{
    public Map<String, Integer> buy = new HashMap<>();

    public String toString() {
        String output = "BuyRequest: {";
        for (Map.Entry<String, Integer> entry : buy.entrySet()) {
            String p = entry.getKey();
            Integer i = entry.getValue();
            output += p + ": " + i + ", ";
        }
        output += "}";
        return output;
    }

}
