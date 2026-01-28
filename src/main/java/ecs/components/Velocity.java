package ecs.components;

public class Velocity implements Component {
    public float vx, vy;

    public String toString() {
        return "Velocity {vX: " + vx + ", vY: " + vy + "}";
    }

    @Override
    public Component clone() {
        Velocity v = new Velocity();
        v.vx = this.vx;
        v.vy = this.vy;
        return v;
    }
}
