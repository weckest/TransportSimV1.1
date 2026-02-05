package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;

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
                bo.age++;
            } else {
                bo = new BuyOrder();
            }

            for(String product: br.buy.keySet()) {
                bo.buy.putIfAbsent(product, br.buy.get(product));
                bo.price += br.buy.get(product);
            }

            if(bo.age == 0) {
                e.addComponent(bo);
                EventManager.emit("Print", new PrintEvent(e.getId()));
            }
        }
    }
}
