package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;

import java.util.List;
import java.util.Set;

public class OutputSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = getActiveEntities(em.getEntities());
        for(Entity e : entities) {
            Set<Class<?>> c = e.getComponentTypes();
            System.out.print("ID: " + e.getId() + ": ");
            for(Class<?> cl : c) {
                System.out.print(e.getComponent(cl));
                System.out.print(", ");
            }
            System.out.println();
        }
    }
}
