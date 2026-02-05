package ecs.components;

import ecs.data.ProductType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory implements Component {
    public Map<String, Integer> inventory = new HashMap<>();

    public String toString() {
        String output = "Inventory: {";
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String p = entry.getKey();
            Integer i = entry.getValue();
            output += p + ": " + i + ", ";
        }
        output += "}";
        return output;
    }

    public Inventory() {
    }

    public Inventory(Inventory inventory) {
        this.inventory = inventory.inventory;
    }
}
