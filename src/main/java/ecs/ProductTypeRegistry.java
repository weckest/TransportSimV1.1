package ecs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecs.data.ProductType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProductTypeRegistry {
    public Map<String, ProductType> productTypes = new HashMap<>();

    public void addProductType(ProductType productType) {
        productTypes.put(productType.tag, productType);
    }

    public ProductType getProductType(String type) {
        return productTypes.get(type);
    }

    public void loadProducts(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new URL(path));
        Iterator<Map.Entry<String, JsonNode>> products = jsonNode.fields();

        products.forEachRemaining(p -> {
            productTypes.put(p.getKey(), new ProductType(p.getKey(), p.getValue().asDouble()));
        });
    }
}
