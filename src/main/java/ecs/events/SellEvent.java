package ecs.events;

import ecs.EntityManager;

import java.util.HashMap;
import java.util.Map;

public class SellEvent implements Event{
    public Map<String, Integer> sell = new HashMap<>();
    public int entityId;
    public EntityManager em;

    public SellEvent() {}

    public SellEvent(Map<String, Integer> sell, int entityId, EntityManager em) {
        this.sell = sell;
        this.entityId = entityId;
        this.em = em;
    }
}
