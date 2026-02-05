package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.components.*;

import java.util.List;

public class StockListSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(Inventory.class, StockList.class);
        for(Entity e: entities) {
            Inventory i = e.getComponent(Inventory.class);
            StockList sl = e.getComponent(StockList.class);
            for(String product: sl.stockList.keySet()) {
                if(i.inventory.getOrDefault(product, 0) < sl.stockList.get(product)) {

                }
            }
        }
    }
}
