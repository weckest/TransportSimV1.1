package ecs.data;

public class ProductType {
    public String tag = "null";
    public double price = -1;

    public String toString() {
        return "Tag: " + tag + ", Price: $" + price;
    }

    public ProductType() {}

    public ProductType(String tag, double price) {
        this.tag = tag;
        this.price = price;
    }
}
