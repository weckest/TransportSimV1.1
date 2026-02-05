package ecs;

import ecs.components.Component;

import java.lang.reflect.Constructor;

public class ComponentCloner {

    public static Component copy(Component original) {
        try {
            Constructor<?> ctor =
                    original.getClass().getConstructor(original.getClass());

            return (Component) ctor.newInstance(original);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Component " + original.getClass().getName() +
                            " must define a copy constructor", e);
        }
    }
}
