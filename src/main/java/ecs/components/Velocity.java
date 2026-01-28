package ecs.components;

public class Velocity implements Component {
    public float vx, vy;

    public String toString() {
        return "Velocity {vX: " + vx + ", vY: " + vy + "}";
    }
}
