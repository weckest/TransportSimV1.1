package ecs.systems;

import ecs.EntityManager;

public interface ISystem {
    public void update(EntityManager em);
}
