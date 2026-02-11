package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.PrintEvent;
import ecs.events.TransportRequest;

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

                    TransportRequest tr = new TransportRequest();
                    tr.sourceId = sellEntity.getId();
                    tr.destinationId = buyEntity.getId();
                    tr.em = em;

                    for(String sellItem: so.sell.keySet()) {
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
                                System.out.println("Orders matched and products moving. BuyPrice: $" + buyPrice + " SellPrice: $" + sellPrice);

                                //change the values of the buy and sell orders
                                so.sell.put(buyItem, so.sell.get(buyItem) - amount);
                                so.price.put(buyItem, so.price.get(buyItem) - sellPrice * amount);
                                bo.buy.put(buyItem, bo.buy.get(buyItem) - amount);
                                bo.price.put(buyItem, bo.price.get(buyItem) - buyPrice * amount);
                            }
                        }
                    }
                    if(!tr.products.isEmpty()) {
                        EventManager.emit("Transport", tr, "Transport Request From OrderSystem\n");
                        break;
                    }
                    //remove items from the sell order if they are no longer selling any
                    for(String item: so.sell.keySet()) {
                        if(so.sell.get(item) == 0) {
                            so.sell.remove(item);
                            so.price.remove(item);
                        }
                    }
                    if(so.sell.isEmpty()) {
                        sellEntity.removeComponent(SellOrder.class);
                    }
                    EventManager.emit("Print", new PrintEvent(sellEntity.getId()), "OrderSystem Sell: ");
                }
            }
            //remove items from the buy order if they are no longer buying any
            for(String item: bo.buy.keySet()) {
                if(bo.buy.get(item) == 0) {
                    bo.buy.remove(item);
                    bo.price.remove(item);
                }
            }
            if(bo.buy.isEmpty()) {
                buyEntity.removeComponent(BuyOrder.class);
            }
            EventManager.emit("Print", new PrintEvent(buyEntity.getId()),"OrderSystem Buy: ");
        }
    }
}
