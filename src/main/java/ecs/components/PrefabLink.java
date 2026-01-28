package ecs.components;

public class PrefabLink implements Component {
    public String prefabId;
    public boolean ready;

    public String toString() {
        return "PrefabLink {id:" + prefabId + ", ready:" + ready + "}";
    }

    @Override
    public Component clone() {
        PrefabLink pl = new PrefabLink();
        pl.ready = this.ready;
        pl.prefabId = this.prefabId;
        return pl;
    }
}
