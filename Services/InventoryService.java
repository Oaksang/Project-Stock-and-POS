package Services;

import DataModels.Product;
import java.util.List;

public class InventoryService {
    List<Product> products;

    public InventoryService(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if (product == null) throw new RuntimeException("Product is unknown");
        products.add(product);
        System.out.println("Product " + product.name() + " added.");
    }

    public void removeBYSKU(String sku) {
        products.removeIf(p -> p.sku().equals(sku));
        System.out.println("Product with SKU " + sku + " removed.");
    }

    public void findBYSKU(String sku) {
        for (Product p : products) {
            if (p.sku().equals(sku)) {
                System.out.println(p);
                return ;
            }
        
        }
    }

    public void setStock(String sku, int newStock) {
        for (Product p : products) {
            if (p.sku().equals(sku)) {
                p.setStock(newStock);
                return ;
            }
        }
    }

    public void increaseStock(String sku, int amount) {
        for (Product p : products) {
            if (p.sku().equals(sku)) {
                p.setStock(p.stock() + amount);
                return ;
            }
        }
    }

    public void decreaseStock(String sku, int amount) {
        for (Product p : products) {
            if (p.sku().equals(sku)) {
                if (p.stock() < amount) throw new RuntimeException("Not enough stock");
                p.setStock(p.stock() - amount);
                return ;
            }
        }
    }

    public void searchBYName(String name) {
        for (Product p : products) {
            if (p.name().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(p);
            }
        }
    }

    // Test InventoryService
}
