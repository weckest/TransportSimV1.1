package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;

import java.util.List;

public class TransportSystem extends BaseSystem {

    public TransportSystem() {
        EventManager.subscribe("Transport", tr -> {
//            this.transport((TransportRequest) tr);
            TransportRequest trans = (TransportRequest) tr;
            Entity e = trans.em.createEntity("transporter");
            //set this to the time it would take to get from source to destination in the future
            trans.eta = 3;
            e.addComponent(trans);
            EventManager.emit("Print", new PrintEvent(e.getId()), "TransportSystem: ");
        });
    }

    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(Transporter.class, TransportRequest.class);
        for(Entity e: entities) {
            TransportRequest tr = e.getComponent(TransportRequest.class);
            EventManager.emit("Print", new PrintEvent(e.getId()), "Transporting: ");
            if(--tr.eta <= 0) {
                transferProducts(tr);
                e.getComponent(Active.class).active = false;
            }
        }
    }

    public void transferProducts(TransportRequest tr) {
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
