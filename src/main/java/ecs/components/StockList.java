package ecs.components;

import java.util.HashMap;
import java.util.Map;

public class StockList implements Component {
    public Map<String, Integer> stockList = new HashMap<>();

    public String toString() {
        String output = "StockList: {";
        for (Map.Entry<String, Integer> entry : stockList.entrySet()) {
            String p = entry.getKey();
            Integer i = entry.getValue();
            output += p + ": " + i + ", ";
        }
        output += "}";
        return output;
    }

    public StockList() {}

    public StockList(StockList stockList) {
        this.stockList = stockList.stockList;
    }
}
