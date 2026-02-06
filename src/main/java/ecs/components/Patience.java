package ecs.components;

public class Patience implements Component {
    public int patience;

    public String toString() {
        return "Patience: {" + patience + "}";
    }

    public Patience() {}

    public Patience(Patience p) {
        this.patience = p.patience;
    }
}
