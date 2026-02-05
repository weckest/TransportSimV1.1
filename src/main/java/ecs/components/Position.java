package ecs.components;

public class Position implements Component {
    public float x, y;

    public String toString() {
        return "Position {X: " + x + ", Y: " + y + "}";
    }

    public Position() {}

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }
}

