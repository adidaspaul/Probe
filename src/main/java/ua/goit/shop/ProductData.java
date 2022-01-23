package ua.goit.shop;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ProductData {

    private static  Map<String, Product> ALL_PRODUCTS = new ConcurrentHashMap<>();

    static{

        Product a = new Product("A",1.25d, 3d, 3L);
        Product b = new Product("B",4.25d);
        Product c = new Product("C",1d, 5d, 6L);
        Product d = new Product("D", 0.75d);

        ALL_PRODUCTS.put(a.getProductId(), a);
        ALL_PRODUCTS.put(b.getProductId(), b);
        ALL_PRODUCTS.put(c.getProductId(), c);
        ALL_PRODUCTS.put(d.getProductId(), d);
    }

    public static Map<String, Product> getAllProducts(){
        return new HashMap(ALL_PRODUCTS);
    }

    public static void setAllProducts(Map<String, Product> allProducts){
        Objects.requireNonNull(allProducts);
        ALL_PRODUCTS = allProducts;
    }

    public static void addOrUpdateProduct(Product product){
        Objects.requireNonNull(product);
        ALL_PRODUCTS.put(product.getProductId(), product);
    }

    public static void removeProductById(String productId){
        Objects.requireNonNull(productId);
        ALL_PRODUCTS.remove(productId);
    }

    public static void removeProductByProduct(Product product){
        Objects.requireNonNull(product);
        ALL_PRODUCTS.remove(product.getProductId(), product);
    }

    public static double calculateCost(String cart){
        if(validateCart(cart)){
            Map<String, Long> productQty = cart.chars()
                    .filter(Character::isAlphabetic)
                    .mapToObj(c -> ALL_PRODUCTS.get(String.valueOf((char)c).toUpperCase(Locale.ROOT)))
                    .collect(Collectors.groupingBy(Product::getProductId,Collectors.mapping(Product::getProductId, Collectors.counting())));
            return productQty.entrySet().stream()
                    .mapToDouble(entry -> ALL_PRODUCTS.get(entry.getKey()).getPriceByQty(entry.getValue()))
                    .sum();
        }else {
            return 0D;
        }
    }
    public static boolean validateCart(String cart){
        if(cart == null || cart.isEmpty()){
            return false;
        }
        if(ALL_PRODUCTS == null || ALL_PRODUCTS.isEmpty()){
            throw new RuntimeException("The Prices Not Available");
        }
        long countOfProducts = cart.chars()
                .filter(Character :: isAlphabetic)
                .filter(c -> !ALL_PRODUCTS.containsKey(String.valueOf((char)c).toUpperCase(Locale.ROOT)))
                .count();
                if(countOfProducts != 0L){
                    throw new RuntimeException("Some IDs are not valid");
                }
                return true;
    }
}
