package ecs.components;

import ecs.EntityManager;
import ecs.registries.PrefabRegistry;

public class Consumer implements Component {
    public double discount;

    public String toString() {
        return "Consumer: {Discount: " + discount * 100 + "%}";
    }

    public Consumer() {}

    public Consumer(Consumer consumer) {
        this.discount = consumer.discount;
    }
}
