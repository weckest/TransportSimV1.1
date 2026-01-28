package ecs.components;

public class Position implements Component {
    public float x, y;

    public String toString() {
        return "Position {X: " + x + ", Y: " + y + "}";
    }

    @Override
    public Component clone() {
        Position p = new Position();
        p.x = this.x;
        p.y = this.y;
        return p;
    }
}

