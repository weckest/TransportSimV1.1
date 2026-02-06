package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;

public class TransportSystem extends BaseSystem {

    public TransportSystem() {
        EventManager.subscribe("Transport", tr -> {
            this.transport((TransportRequest) tr);
        });
    }

    @Override
    public void update(EntityManager em) {

    }

    public void transport(TransportRequest tr) {
        Entity source = tr.em.getEntity(tr.sourceId);
        Inventory sourceInventory = source.getComponent(Inventory.class);
        Entity destination = tr.em.getEntity(tr.destinationId);
        Inventory destInventory = destination.getComponent(Inventory.class);

        for(String item: tr.products.keySet()) {
            sourceInventory.inventory.remove(item, tr.products.get(item));
            destInventory.inventory.put(item, destInventory.inventory.getOrDefault(item, 0) + tr.products.get(item));
        }

    }
}
