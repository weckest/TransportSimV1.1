package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.events.SellEvent;
import ecs.events.SellRequest;

public class SellSystem extends BaseSystem {

    public SellSystem() {
        EventManager.subscribe("Sell", e -> {
            SellEvent se = (SellEvent) e;
            Entity entity = se.em.getEntity(se.entityId);
            SellRequest sr = new SellRequest();
            sr.sell = se.sell;
            entity.addComponent(sr);
        });
    }

    @Override
    public void update(EntityManager em) {

    }
}
