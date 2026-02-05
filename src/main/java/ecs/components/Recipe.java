package ecs.components;

import ecs.data.ProductType;

import java.util.HashMap;
import java.util.Map;

public class Recipe implements Component{
    public Map<String, Integer> input;
    public Map<String, Integer> output;
    public int time;
    public int cooldown;

    public String toString() {
        String output = "Recipe: {";
        output += "cooldown: " + cooldown + ", time: " + time + "}";


        return output;
    }

    public Recipe() {
        input = new HashMap<>();
        output = new HashMap<>();
    }

    public Recipe(Recipe recipe) {
        this.input = recipe.input;
        this.output = recipe.output;
        this.cooldown = recipe.cooldown;
        this.time = recipe.time;
    }
}
