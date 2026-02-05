package ecs.systems;

import ecs.Entity;
import ecs.EntityManager;
import ecs.EventManager;
import ecs.components.*;
import ecs.events.*;

import java.util.List;

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
            boolean flag = RecipeSystem.canProducer(r, i);

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
                //once we make the new products add a print event to the entity to print the new inventory
                EventManager.emit("Print", new PrintEvent(e.getId()));
            }


        }
    }
}
