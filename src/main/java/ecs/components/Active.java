package ecs.components;

public class Active implements Component {
    public boolean active = true;

    public String toString() {
        return "Active {" + active + "}";
    }

    public Active() {}

    public Active(Active active) {
        this.active = active.active;
    }
}
