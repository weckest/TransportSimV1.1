package ecs;

import ecs.components.Component;
import ecs.systems.BaseSystem;

import java.util.LinkedList;
import java.util.List;

public class EntityManager {
    private int nextId = 0;
    private final List<Entity> entities = new LinkedList<>();
    private final List<BaseSystem> systems = new LinkedList<>();
    private PrefabRegistry pr;

    public EntityManager(PrefabRegistry pr) {
        this.pr = pr;
    }

    public Entity createEntity() {
        Entity e = new Entity(nextId++);
        entities.add(e);
        return e;
    }

    public Entity createEntity(Prefab prefab) {
        Entity e = EntityFactory.createFromPrefab(this, prefab);
        return e;
    }

    public Entity createEntity(String prefabId) {
        Entity e = null;
        try {
            e = createEntity(pr.get(prefabId));
        } catch(Exception exception) {
            System.err.println(exception);
        }
        return e;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getEntitiesWithComponents(Class<?>... comps) {
        List<Entity> out = new LinkedList<>();
        for(Entity e : entities) {
            boolean good = true;
            for (Class<?> comp : comps) {
                if(!e.hasComponent(comp)) {
                    good = false;
                    break;
                }
            }
            if(good) {
                out.add(e);
            }
        }
        return out;
    }

    public Entity getEntity(int i) {
        return entities.get(i);
    }

    public void addSystem(BaseSystem s) {
        systems.add(s);
    }


    public <T> void activateSystem(T type) {
        for(BaseSystem s : systems) {
            if(s.getClass().equals(type)) {
                s.setActive(true);
            }
        }
    }

    public void update(float dt) {
        for(BaseSystem s : systems) {
            if(s.isActive()) {
                s.update(this);
            }
        }
    }
}
