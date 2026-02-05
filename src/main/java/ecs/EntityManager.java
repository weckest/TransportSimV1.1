package ecs;

import ecs.components.Active;
import ecs.components.Component;
import ecs.events.PrintEvent;
import ecs.systems.BaseSystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private int nextId = 0;
    private final List<Entity> entities = new LinkedList<>();
    private final Map<Class<? extends BaseSystem>, BaseSystem> systems = new HashMap<>();
    private PrefabRegistry pr;
    private ProductTypeRegistry ptr;

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
        EventManager.emit("Print", new PrintEvent(e.getId()));
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
            if(e.getComponent(Active.class).active && e.hasComponents(comps)) {
                out.add(e);
            }
        }
        return out;
    }

    public Entity getEntity(int i) {
        return entities.get(i);
    }

    public void addSystem(BaseSystem s) {
        systems.put(s.getClass(), s);
    }

    public ProductTypeRegistry getProductTypeRegistry() {
        return ptr;
    }


    public <T extends BaseSystem> void activateSystem(T type) {
        systems.get(type).setActive(true);
    }

    public void update(float dt) {
        for(BaseSystem s : systems.values()) {
            if(s.isActive()) {
                s.update(this);
            }
        }
    }
}
