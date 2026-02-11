package ecs;

import com.fasterxml.jackson.databind.JsonNode;
import ecs.components.Component;

@FunctionalInterface
public interface ComponentParser {
    Component parse(JsonNode data);
}


