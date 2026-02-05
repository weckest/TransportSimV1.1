package ecs.systems;

import ecs.EntityManager;
import ecs.components.*;

public class RecipeSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        return;
    }

    public static boolean canProducer(Recipe r, Inventory i) {
        boolean flag = true;
        for(String input: r.input.keySet()) {
            //check if any of the required items are missing and then skip this entity
            if(InventorySystem.getAmount(i, input) < r.input.get(input)) {
                flag = false;
            }
        }
        return flag;
    }
}
