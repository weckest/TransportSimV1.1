package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.components.Position;
import ecs.components.Velocity;

import java.util.List;

public class MovementSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = getActiveEntities(em.getEntities());
        for(Entity e : entities) {
            if(e.hasComponents(Position.class, Velocity.class)) {
                Position p = e.getComponent(Position.class);
                Velocity v = e.getComponent(Velocity.class);
                p.x += v.vx;
                p.y += v.vy;
            }
        }
    }
}
