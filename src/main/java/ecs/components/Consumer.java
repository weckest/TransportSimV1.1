package ecs.components;

import ecs.EntityManager;
import ecs.registries.PrefabRegistry;

public class Consumer implements Component {

    public String toString() {
        return "Consumer";
    }

    public Consumer() {}

    public Consumer(Consumer consumer) {

    }
}
