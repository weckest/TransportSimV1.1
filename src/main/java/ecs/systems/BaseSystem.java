package ecs.systems;

import ecs.Entity;
import ecs.components.Active;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseSystem implements ISystem {
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Entity> getActiveEntities(List<Entity> entities) {
        List<Entity> active = new LinkedList<>();
        for(Entity e : entities) {
            Active a = e.getComponent(Active.class);
            if (a.active) {
                active.add(e);
            }
        }
        return active;
    }
}
