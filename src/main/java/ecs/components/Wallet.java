package ecs.components;

public class Wallet implements Component {
    public double money;

    public String toString() {
        return "Wallet: {$" + money + "}";
    }

    public Wallet() {}

    public Wallet(Wallet wallet) {
        this.money = wallet.money;
    }
}
