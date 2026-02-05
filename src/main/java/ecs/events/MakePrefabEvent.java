package ecs.events;

import ecs.components.Component;

public class MakePrefabEvent implements Event {
    public String prefabId;

    public MakePrefabEvent(String prefabId) {
        this.prefabId = prefabId;
    }
}
