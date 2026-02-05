package ecs.components;

public class Producer implements Component{
    public double profitMargin;

    public String toString() {
        return "Producer: {" + profitMargin*100 + "%}";
    }

    public Producer() {}

    public Producer(Producer producer) {
        this.profitMargin = producer.profitMargin;
    }
}
