package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.PrintEvent;

import java.util.List;

public class SellOrderSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(SellOrder.class, Patience.class);
        for(Entity e: entities) {
            SellOrder so = e.getComponent(SellOrder.class);
            Patience p = e.getComponent(Patience.class);
            so.age++;
            if(so.age % p.patience == 0) {
                for(String item: so.sell.keySet()) {
                    double newPrice = so.price.get(item) * 0.9;
                    so.price.put(item, newPrice);
                }
                if(em.flags.print && em.flags.sell) {
                    EventManager.emit("Print", new PrintEvent(e.getId()), "SellOrderSystem: ");
                }
            }
        }
    }
}
