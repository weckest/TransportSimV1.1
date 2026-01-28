package ecs.components;

public class PrefabLink implements Component {
    public String prefabId;
    public boolean ready;

    public String toString() {
        return "PrefabLink {" + prefabId + "}";
    }
}
