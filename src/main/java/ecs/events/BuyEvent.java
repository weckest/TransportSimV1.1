package ecs.events;

import ecs.EntityManager;

import java.util.HashMap;
import java.util.Map;

public class BuyEvent implements Event {
    public Map<String, Integer> buy = new HashMap<>();
    public int entityId;
    public EntityManager em;

    public BuyEvent() {}

    public BuyEvent(Map<String, Integer> buy, int entityId, EntityManager em) {
        this.buy = buy;
        this.entityId = entityId;
        this.em = em;
    }
}
