package ecs;

import ecs.components.Component;

import java.util.HashMap;
import java.util.Map;

public class Prefab {
    public Map<Class<?>, Component> components = new HashMap<>();
    
}
