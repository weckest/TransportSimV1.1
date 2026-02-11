package ecs;

import ecs.events.Event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class EventManager {
    private static final Map<String, List<Consumer<Event>>> subscriptions = new HashMap<>();

    public static void subscribe(String eventType, Consumer<Event> callback) {
        List<Consumer<Event>> callbacks = subscriptions.getOrDefault(eventType, new LinkedList<>());
        callbacks.add(callback);
        subscriptions.put(eventType, callbacks);
    }

    public static void emit(String eventType, Event event, String caller) {
        System.out.print(caller);
        emit(eventType, event);
    }

    public static void emit(String eventType, Event event) {
        List<Consumer<Event>> listeners = subscriptions.get(eventType);
        if(listeners == null) {
            System.err.println("No Listeners for Event: " + eventType);
            return;
        }
        for(Consumer<Event> consumer: listeners) {
            consumer.accept(event);
        }
    }
}
