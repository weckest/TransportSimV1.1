package ecs.components;

public class Velocity implements Component {
    public float vx, vy;

    public String toString() {
        return "Velocity {vX: " + vx + ", vY: " + vy + "}";
    }

    public Velocity() {}

    public Velocity(Velocity velocity) {
        this.vx = velocity.vx;
        this.vy = velocity.vy;
    }
}
