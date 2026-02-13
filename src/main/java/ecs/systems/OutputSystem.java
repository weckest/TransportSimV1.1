package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.events.*;

import java.util.List;
import java.util.Set;

public class OutputSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = getActiveEntities(em.getEntities());
        for(Entity e : entities) {
            if(e.hasComponent(PrintEvent.class)) {
                e.removeComponent(PrintEvent.class);
                printEntity(e);
            }
        }
    }

    public void printEntity(Entity e) {
        Set<Class<?>> c = e.getComponentTypes();
        System.out.print("ID: " + e.getId() + ": \n");
        for (Class<?> cl : c) {
            System.out.print("  ");
            System.out.print(e.getComponent(cl));
            System.out.print(", \n");
        }
        System.out.println();
    }
}
