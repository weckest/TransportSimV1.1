package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;
import ecs.registries.ProductTypeRegistry;

import java.util.List;

public class BuyRequestSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(BuyRequest.class);
        for(Entity e: entities) {
            BuyRequest br = e.getComponent(BuyRequest.class);
            Consumer c = e.getComponent(Consumer.class);
            if(c == null) {
                e.removeComponent(BuyRequest.class);
                continue;
            }
            BuyOrder bo;
            if(e.hasComponent(BuyOrder.class)) {
                bo = e.getComponent(BuyOrder.class);
            } else {
                bo = new BuyOrder();
            }

            for(String product: br.buy.keySet()) {
                if(!bo.buy.containsKey(product)) {
                    bo.buy.put(product, br.buy.get(product));
                    bo.price.put(product, bo.buy.get(product) * em.getRegistry(ProductTypeRegistry.class).getProductType(product).price * (1 - c.discount));
                }
            }

            if(bo.age == 0) {
                e.addComponent(bo);
                e.removeComponent(BuyRequest.class);
                if(em.flags.print && em.flags.buy) {
                    EventManager.emit("Print", new PrintEvent(e.getId()), "BuyRequestSystem: ");
                }
            }
        }
    }
}
