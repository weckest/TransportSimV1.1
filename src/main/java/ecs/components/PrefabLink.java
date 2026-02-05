package ecs.components;

public class PrefabLink implements Component {
    public String prefabId;

    public String toString() {
        return "PrefabLink {id:" + prefabId + "}";
    }

    public PrefabLink() {}

    public PrefabLink(PrefabLink prefabLink) {
        this.prefabId = prefabLink.prefabId;
    }
}
