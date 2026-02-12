package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.PrintEvent;
import ecs.events.TransportRequest;

import java.util.LinkedList;
import java.util.List;

public class OrderSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> buyEntities = em.getEntitiesWithComponents(BuyOrder.class, Wallet.class, Inventory.class);
        List<Entity> sellEntities = em.getEntitiesWithComponents(SellOrder.class, Wallet.class, Inventory.class);
        //remake this in the future to sort the product orders but price and then
        // check highest buy price of the item against lowest sell price of the item
        for(Entity buyEntity: buyEntities) {
            BuyOrder bo = buyEntity.getComponent(BuyOrder.class);
            for(String buyItem: bo.buy.keySet()) {
                int buyAmount = bo.buy.get(buyItem);
                double buyPrice = bo.price.get(buyItem) / buyAmount;

                for(Entity sellEntity: sellEntities) {
                    SellOrder so = sellEntity.getComponent(SellOrder.class);

                    if(so == null) {
                        continue;
                    }

                    TransportRequest tr = new TransportRequest();
                    tr.sourceId = sellEntity.getId();
                    tr.destinationId = buyEntity.getId();
                    tr.em = em;

                    if(so.sell.containsKey(buyItem)) {
                        String sellItem = buyItem;
                        int sellAmount = so.sell.get(sellItem);
                        double sellPrice = so.price.get(sellItem) / sellAmount;

                        //check for lower sell price (add negotiations in the future)
                        if(sellPrice < buyPrice) {
                            double price = (buyPrice + sellPrice) / 2;
                            int amount = Math.min(buyAmount, sellAmount);
                            double totalPrice = price * amount;

                            //add data to the transport request if the buyer can afford the purchase
                            Wallet buyWallet = buyEntity.getComponent(Wallet.class);
                            Wallet sellWallet = sellEntity.getComponent(Wallet.class);
                            if(buyWallet.money >= totalPrice) {
                                buyWallet.money -= totalPrice;
                                sellWallet.money += totalPrice;

                                tr.products.put(buyItem, amount);
                                System.out.println("Orders matched for " + amount + " " + buyItem +
                                        " and products moving. BuyPrice: $" + buyPrice + " SellPrice: $" +
                                        sellPrice + " Price: $" + price);

                                //change the values of the buy and sell orders
                                so.sell.put(buyItem, sellAmount - amount);
                                so.price.put(buyItem, (sellPrice * sellAmount) - sellPrice * amount);
                                bo.buy.put(buyItem, buyAmount - amount);
                                bo.price.put(buyItem, (buyPrice * buyAmount) - buyPrice * amount);
                            }
                        }
                    }
                    if(!tr.products.isEmpty()) {
                        EventManager.emit("Transport", tr, "Transport Request From OrderSystem\n");
                    }
                    //remove items from the sell order if they are no longer selling any
                    List<String> items = new LinkedList<>();
                    so.sell.forEach((k, v) -> {
                        if(v == 0) {
                            items.add(k);
                        }
                    });
                    items.forEach(k -> {
                        so.price.remove(k);
                        so.sell.remove(k);
                    });
                    if(so.sell.isEmpty()) {
                        sellEntity.removeComponent(SellOrder.class);
                    }
                    EventManager.emit("Print", new PrintEvent(sellEntity.getId()), "OrderSystem Sell: ");
                }
            }
            //remove items from the buy order if they are no longer buying any
            List<String> items = new LinkedList<>();
            bo.buy.forEach((k, v) -> {
                if(v == 0) {
                    items.add(k);
                }
            });
            items.forEach(k -> {
                bo.buy.remove(k);
                bo.price.remove(k);
            });
            if(bo.buy.isEmpty()) {
                buyEntity.removeComponent(BuyOrder.class);
            }
            EventManager.emit("Print", new PrintEvent(buyEntity.getId()),"OrderSystem Buy: ");
        }
    }
}
