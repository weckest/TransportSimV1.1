package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;
import ecs.registries.ProductTypeRegistry;

import java.util.List;

public class SellRequestSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(SellRequest.class, Producer.class);
        for(Entity e: entities) {
            SellRequest sr = e.getComponent(SellRequest.class);
            Producer p = e.getComponent(Producer.class);
            SellOrder so;
            if(e.hasComponent(SellOrder.class)) {
                so = e.getComponent(SellOrder.class);
            } else {
                so = new SellOrder();
            }

            for(String item: sr.sell.keySet()) {
                so.sell.put(item, sr.sell.get(item));
                so.price.put(item, so.sell.get(item) * em.getRegistry(ProductTypeRegistry.class).getProductType(item).price * (1 + p.profitMargin));
                sr.sell.remove(item);
            }

            if(sr.sell.isEmpty()) {
                e.removeComponent(SellRequest.class);
            }

            if(so.age == 0) {
                e.addComponent(so);
            }
//            EventManager.emit("Print", new PrintEvent(e.getId()));
        }
    }
}
