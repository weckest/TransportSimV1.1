package ecs.registries;

import ecs.Prefab;

import java.util.*;

public class Registry<T> {
    private final Map<String, T> registry = new HashMap<>();

    public T get(String id) throws Exception {
        T p = registry.get(id);
        if(p == null) {
            throw new Exception("\"" + id + "\" does not exist");
        }
        return p;
    }

    public void add(String id, T data) {
        registry.put(id, data);
    }

    public Set<String> getKeys() {
        return registry.keySet();
    }

    public Collection<T> getValues() {
        return registry.values();
    }
}
