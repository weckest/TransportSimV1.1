package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.events.BuyEvent;
import ecs.events.BuyRequest;

public class BuySystem extends BaseSystem {

    public BuySystem() {
        EventManager.subscribe("Buy", e -> {
            BuyEvent be = (BuyEvent)e;
            Entity entity = be.em.getEntity(be.entityId);
            BuyRequest br = new BuyRequest();
            br.buy = be.buy;
            entity.addComponent(br);
        });
    }

    @Override
    public void update(EntityManager em) {

    }
}
