package ecs.events;

import ecs.EntityManager;

import java.util.HashMap;
import java.util.Map;

public class TransportRequest implements Event {
    public int sourceId;
    public int destinationId;
    public Map<String, Integer> products = new HashMap<>();
    public EntityManager em;

}
