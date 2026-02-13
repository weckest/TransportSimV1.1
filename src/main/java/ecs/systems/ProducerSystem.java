package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerSystem extends BaseSystem {
    @Override
    public void update(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(Producer.class, Inventory.class, Recipe.class);
        for(Entity e : entities) {
            Recipe r = e.getComponent(Recipe.class);
            Inventory i = e.getComponent(Inventory.class);

            if(r.time > 0) {
                r.time--;
                break;
            }

            //flag to indicate if there are enough products to make new product
            boolean flag = RecipeSystem.canProduce(r, i);

            //check to see if we can make the new product
            if(flag) {
                //remove the input products from the inventory
                for(String input: r.input.keySet()) {
                    InventorySystem.remove(i, input, r.input.get(input));
                }

                //make the amount of each output product in the recipe
                for(String output : r.output.keySet()) {
                    InventorySystem.add(i, output, r.output.get(output));
                }

                r.time = r.cooldown;


                //after making an item. make a sell request of the products that are not needed for the recipe
                Map<String, Integer> sell = new HashMap<>();
                for(String item: i.inventory.keySet()) {
                    if(!RecipeSystem.needsItem(r, item)) {
                        sell.put(item, i.inventory.get(item));
                    }
                }
                //dont add the component if we arent selling anything
                if(!sell.isEmpty()) {
                    EventManager.emit("Sell", new SellEvent(sell, e.getId(), em));
                    if(em.flags.print && em.flags.sell) {
                        EventManager.emit("Print", new PrintEvent(e.getId()), "ProducerSystem: ");
                    }

                }
            } else {
                //not enough of something to make the recipe. so make a request for the required items
                Map<String, Integer> missing = RecipeSystem.getMissingItems(r, i);
                EventManager.emit("Buy", new BuyEvent(missing, e.getId(), em));
            }
        }
    }
}
