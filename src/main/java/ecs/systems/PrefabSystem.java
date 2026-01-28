package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.components.PrefabLink;

import java.util.List;

public class PrefabSystem extends BaseSystem {

    @Override
    public void update(EntityManager em) {
        List<Entity> entities = getActiveEntities(em.getEntities());
        for(Entity e : entities) {
            if(e.hasComponent(PrefabLink.class)) {
                PrefabLink pl = e.getComponent(PrefabLink.class);
                if(pl.ready) {
                    em.createEntity(pl.prefabId);
                }
            }
        }
    }
}
