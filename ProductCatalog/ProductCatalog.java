package ProductCatalog;

import java.util.ArrayList;
import java.util.Comparator;

import DataModels.Product;

public class ProductCatalog {
    ArrayList<Product> products;

    public ProductCatalog() {
        products = new ArrayList<>();
    }

    private void CheckRep() {
        for (Product p : products) {
            if (p.price() < 0) {
                throw new RuntimeException("Price cannot be negative");
            }
            if (p.stock() < 0) {
                throw new RuntimeException("Stock cannot be negative");
            }
        }
    }

    public void getAll(){
        for (Product p : products) {
            System.out.println(p);
        }
        CheckRep();
    }

    public void getBySKU(String sku){
        for (Product p : products) {
            if(p.sku().equals(sku)){
                System.out.println(p);
                return;
            }
        }
        System.out.println("Product not found");
        CheckRep();
    }

    public void searchByName(String name){
        for (Product p : products) {
            if(p.name().toLowerCase().contains(name.toLowerCase())){
                System.out.println(p);
            }
        }
        CheckRep();
    }

    public void sortByPrice(boolean ascending) {
    if (ascending) {
        products.sort(Comparator.comparing(Product::getPrice));
    } else {
        products.sort(Comparator.comparing(Product::getPrice).reversed());
        }
    CheckRep();
    }

    public void sortByName(boolean ascending) {
        if (ascending) {
            products.sort(Comparator.comparing(Product::name));
        } else {
            products.sort(Comparator.comparing(Product::name).reversed());
        }
        CheckRep();
    }

    public void lowStockAlert(int threshold) {
        for (Product p : products) {
            if (p.stock() < threshold) {
                System.out.println("Low stock alert: " + p);
            }
        }
        CheckRep();
    }
}
