package ecs.registries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecs.data.ProductType;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProductTypeRegistry extends Registry<ProductType> {

    public void addProductType(ProductType productType) {
        add(productType.tag, productType);
    }

    public ProductType getProductType(String type) {
        try {
            return get(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadProducts(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new URL(path));
        Iterator<Map.Entry<String, JsonNode>> products = jsonNode.fields();

        products.forEachRemaining(p -> {
            add(p.getKey(), new ProductType(p.getKey(), p.getValue().asDouble()));
        });
    }
}
