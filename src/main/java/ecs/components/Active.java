package ecs.components;

public class Active implements Component {
    public boolean active = true;

    public String toString() {
        return "Active {" + active + "}";
    }

    @Override
    public Component clone() {
        Active a = new Active();
        a.active = this.active;
        return a;
    }
}
