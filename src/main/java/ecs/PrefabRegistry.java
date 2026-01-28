package ecs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecs.components.*;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class PrefabRegistry {
    private final Map<String, Prefab> prefabs;
    private ObjectMapper mapper;
    private JsonNode jsonNode;
    Map<String, ComponentParser> componentParsers;

    public PrefabRegistry() {
        prefabs = new HashMap<>();
        componentParsers = new HashMap<>();

        init();

    }

    public Prefab get(String id) throws Exception{
        Prefab p = prefabs.get(id);
        if(p == null) {
            throw new Exception("Prefab: \"" + id + "\" does not exist");
        }
        return p;
    }

    public void loadPrefabs(String path) throws IOException {
        mapper = new ObjectMapper();
        jsonNode = mapper.readTree(new URL(path));
        Iterator<Map.Entry<String, JsonNode>> prefabs = jsonNode.fields();

        while(prefabs.hasNext()) {
            loadPrefab(prefabs.next());
        }
    }

    public void loadPrefab(String id) {
        loadPrefab(new AbstractMap.SimpleEntry<String, JsonNode>(id, jsonNode.get(id)));
    }

    public void loadPrefab(Map.Entry<String, JsonNode> entry) {
        Prefab prefab = new Prefab();
        this.prefabs.put(entry.getKey(), prefab);
        JsonNode prefabNode = entry.getValue();
        JsonNode componentsNode = prefabNode.get("components");

        Iterator<Map.Entry<String, JsonNode>> components = componentsNode.fields();
        while(components.hasNext()) {
            var comp = components.next();
            String componentName = comp.getKey();
            JsonNode data = comp.getValue();

            ComponentParser parser = componentParsers.get(componentName);
            if(parser != null) {
                Component component = parser.parse(data);
                prefab.components.put(component.getClass(), component);
            } else {
                System.out.println("Unknown component: " + componentName);
            }
        }
    }

    public void init() {
        componentParsers.put("Position", data -> {
            Position p = new Position();
            p.x = (float) data.get("x").asDouble();
            p.y = (float) data.get("y").asDouble();
            return p;
        });

        componentParsers.put("Velocity", data -> {
            Velocity v = new Velocity();
            v.vx = (float) data.get("vx").asDouble();
            v.vy = (float) data.get("vy").asDouble();
            return v;
        });

        componentParsers.put("PrefabLink", data -> {
            PrefabLink pl = new PrefabLink();
            pl.prefabId = data.get("prefabId").asText();
            return pl;
        });
    }

    public void printPrefabs() {
        for(Prefab p : prefabs.values()) {
            System.out.println(p.components);
        }
    }
}
