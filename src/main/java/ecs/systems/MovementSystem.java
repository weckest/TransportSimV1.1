package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.components.*;

import java.util.List;

public class MovementSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(Position.class, Velocity.class);
        for(Entity e : entities) {
            Position p = e.getComponent(Position.class);
            Velocity v = e.getComponent(Velocity.class);
            p.x += v.vx;
            p.y += v.vy;
        }
    }
}
