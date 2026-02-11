package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.PrintEvent;

import java.util.List;

public class BuyOrderSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(BuyOrder.class, Patience.class);
        for(Entity e: entities) {
            BuyOrder bo = e.getComponent(BuyOrder.class);
            Patience p = e.getComponent(Patience.class);
            bo.age++;
            if(bo.age % p.patience == 0) {
                for(String item: bo.price.keySet()) {
                    double newPrice = bo.price.get(item) * 1.1;
                    bo.price.put(item, newPrice);
//                    EventManager.emit("Print", new PrintEvent(e.getId()), "BuyOrderSystem: ");
                }
            }
        }
    }
}
