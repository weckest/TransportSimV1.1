package ecs.components;

public class Output implements Component {
    public String output;

    public String toString() {
        return output;
    }

    @Override
    public Component clone() {
        Output o = new Output();
        o.output = this.output;
        return o;
    }
}
