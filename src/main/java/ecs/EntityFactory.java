package ecs;

import ecs.components.Component;

public class EntityFactory {

    public static Entity createFromPrefab(
            EntityManager em, Prefab prefab
    ) {
        Entity e = em.createEntity();

        for(Component c : prefab.components.values()) {
            e.addComponent(c.clone());
        }

        return e;
    }
}
