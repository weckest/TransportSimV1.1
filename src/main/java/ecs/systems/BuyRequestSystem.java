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
            BuyOrder bo;
            if(e.hasComponent(BuyOrder.class)) {
                bo = e.getComponent(BuyOrder.class);
            } else {
                bo = new BuyOrder();
            }

            for(String product: br.buy.keySet()) {
                bo.buy.put(product, br.buy.get(product));
                bo.price.put(product, bo.buy.get(product) * em.getRegistry(ProductTypeRegistry.class).getProductType(product).price);
            }

            if(bo.age == 0) {
                e.addComponent(bo);
                e.removeComponent(BuyRequest.class);
                EventManager.emit("Print", new PrintEvent(e.getId()), "BuyRequestSystem: ");
            }
        }
    }
}
