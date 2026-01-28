package ecs.components;

public class Position implements Component {
    public float x, y;

    public String toString() {
        return "Position {X: " + x + ", Y: " + y + "}";
    }
}

