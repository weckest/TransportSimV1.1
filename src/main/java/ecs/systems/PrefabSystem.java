package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.components.PrefabLink;
import ecs.events.EventTag;
import ecs.events.MakePrefabEvent;
import ecs.events.PrintEvent;

import java.util.List;

public class PrefabSystem extends BaseSystem {

    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(PrefabLink.class, MakePrefabEvent.class);
        for(Entity e : entities) {
            MakePrefabEvent pl = e.getComponent(MakePrefabEvent.class);
            em.createEntity(pl.prefabId).addComponent(new PrintEvent());
            e.removeComponent(MakePrefabEvent.class);
        }
    }
}
