package ecs;

import ecs.data.ProductType;

import java.util.HashMap;
import java.util.Map;

public class ProductTypeRegistry {
    public static final Map<String, ProductType> productTypes = new HashMap<>();

    public static boolean addProductType(ProductType productType) {
        return productTypes.putIfAbsent(productType.tag, productType).equals(productType);
    }

    public static ProductType getProductType(String type) {
        return productTypes.get(type);
    }
}
