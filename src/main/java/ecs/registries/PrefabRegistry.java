package ecs.registries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecs.ComponentParser;
import ecs.Prefab;
import ecs.components.*;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class PrefabRegistry extends Registry<Prefab> {
    private ObjectMapper mapper;
    private JsonNode jsonNode;
    private Map<String, ComponentParser> componentParsers;

    public PrefabRegistry() {
        componentParsers = new HashMap<>();
        init();
    }

    public void addParser(String id, ComponentParser parser) {
        componentParsers.put(id, parser);
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
        add(entry.getKey(), prefab);
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
                System.err.println("Unknown component: " + componentName);
            }
        }
    }

    private void init() {
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

        componentParsers.put("Inventory", data -> {
            Inventory i = new Inventory();
            data.get("inventory").fields().forEachRemaining(f -> {
                i.inventory.put(f.getKey(), f.getValue().asInt());
            });
            return i;
        });

        componentParsers.put("Producer", data -> {
            Producer p = new Producer();
            p.profitMargin = data.get("profitMargin").asDouble();
            return p;
        });

        componentParsers.put("Recipe", data -> {
            Recipe r = new Recipe();
            data.get("input").fields().forEachRemaining(f -> {
                r.input.put(f.getKey(), f.getValue().asInt());
            });
            data.get("output").fields().forEachRemaining(f -> {
                r.output.put(f.getKey(), f.getValue().asInt());
            });
            r.cooldown = data.get("cooldown").asInt();
            r.time = data.get("time").asInt();
            return r;
        });

        componentParsers.put("Consumer", data -> {
            Consumer c = new Consumer();
            return c;
        });

        componentParsers.put("Wallet", data -> {
            Wallet w = new Wallet();
            w.money = data.get("money").asDouble();
            return w;
        });

        componentParsers.put("StockList", data -> {
            StockList sl = new StockList();
            data.get("stocklist").fields().forEachRemaining(f -> {
                sl.stockList.put(f.getKey(), f.getValue().asInt());
            });
            return sl;
        });

        componentParsers.put("Patience", data -> {
           Patience p = new Patience();
           p.patience = data.get("patience").asInt();
           return p;
        });
    }

    public void printPrefabs() {
        for(Prefab p : getValues()) {
            System.out.println(p.components);
        }
    }
}
