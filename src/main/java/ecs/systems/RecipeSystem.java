package ecs.systems;

import ecs.EntityManager;
import ecs.components.*;
import ecs.data.ProductType;

import java.util.HashMap;
import java.util.Map;

public class RecipeSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        return;
    }

    public static boolean canProduce(Recipe r, Inventory i) {
        boolean flag = true;
        for(String input: r.input.keySet()) {
            //check if any of the required items are missing and then skip this entity
            if(InventorySystem.getAmount(i, input) < r.input.get(input)) {
                flag = false;
            }
        }
        return flag;
    }

    public static Map<String, Integer> getMissingItems(Recipe r, Inventory i) {
        Map<String, Integer> missing = new HashMap<>();
        for(String item: r.input.keySet()) {
            if(r.input.get(item) > i.inventory.getOrDefault(item, 0)) {
                missing.put(item, r.input.get(item) - i.inventory.getOrDefault(item, 0));
            }
        }
        return missing;
    }

    public static boolean needsItem(Recipe r, String item) {
        for(String i: r.input.keySet()) {
            if(i.equals(item)) {
                return true;
            }
        }

        return false;
    }
}
