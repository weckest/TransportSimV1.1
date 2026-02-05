package ecs.systems;

import ecs.EntityManager;
import ecs.ProductTypeRegistry;
import ecs.components.Inventory;
import ecs.data.ProductType;

public class InventorySystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        return;
    }

    public static Inventory add(Inventory i, String productType, int amount) {
        i.inventory.put(productType, amount + i.inventory.getOrDefault(productType, 0));
        return i;
    }

    public static Inventory remove(Inventory i, String productType, int amount) {
        i.inventory.put(productType, i.inventory.get(productType) - amount);
        return i;
    }

    public static int getAmount(Inventory i, String productType) {
        return i.inventory.get(productType);
    }
}
