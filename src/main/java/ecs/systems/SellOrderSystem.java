package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.components.*;

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
                    double newPrice = so.price.get(item) * 0.99;
                    so.price.put(item, newPrice);
                }
            }
        }
    }
}
