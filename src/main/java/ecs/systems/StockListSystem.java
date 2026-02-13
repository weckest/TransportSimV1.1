package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockListSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(Inventory.class, StockList.class);
        for(Entity e: entities) {
            Inventory i = e.getComponent(Inventory.class);
            StockList sl = e.getComponent(StockList.class);

            if(e.hasComponent(BuyRequest.class)) {
                continue;
            }

            Map<String, Integer> want = new HashMap<>();
            BuyOrder bo = e.getComponent(BuyOrder.class);
            for(String product: sl.stockList.keySet()) {
                if(i.inventory.getOrDefault(product, 0) < sl.stockList.get(product)) {
                    int diff = sl.stockList.get(product) - i.inventory.getOrDefault(product, 0);
                    //dont request to buy an item that we have already made an order for
                    if(bo == null) {
                        want.put(product, diff);
                    } else {
                        if(!bo.buy.containsKey(product)) {
                            want.put(product, diff);
                        }
                    }
                }
            }
            if(!want.isEmpty()) {
                EventManager.emit("Buy", new BuyEvent(want, e.getId(), em));
            }
        }
    }
}
