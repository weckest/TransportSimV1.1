package ecs;

import ecs.components.Active;
import ecs.components.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Entity {
    private final int id;
    private final Map<Class<?>, Object> components = new HashMap<>();

    public Entity(int id) {
        this.id = id;
        addComponent(new Active());
    }

    public int getId() {
        return id;
    }

    public <T> T addComponent(T component) {
        components.put(component.getClass(), component);
        return component;
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type) {
        return (T) components.get(type);
    }

    public <T> void removeComponent(Class<T> type) {
        components.remove(type);
    }

    public boolean hasComponent(Class<?> type) {
        return components.containsKey(type);
    }

    public boolean hasComponents(Class<?>... types) {
        for(Class<?> type : types) {
            if(!hasComponent(type)) {
                return false;
            }
        }
        return true;
    }

    public Set<Class<?>> getComponentTypes() {
        return components.keySet();
    }

    public void printComponents() {
        components.forEach((c, o) -> {
            System.out.println(o);
        });
    }
}
