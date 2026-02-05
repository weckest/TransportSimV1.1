package ecs.events;


public class PrintEvent implements Event {
    public int entityId;

    public PrintEvent() {}

    public PrintEvent(int entityId) {
        this.entityId = entityId;
    }
}
