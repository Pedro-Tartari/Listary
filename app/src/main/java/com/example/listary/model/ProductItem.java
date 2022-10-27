package com.example.listary.model;

public class ProductItem {

    private String productName, productLocal;
    private double productPrice;

    public ProductItem(String productName, String productLocal, double productPrice) {
        this.productName = productName;
        this.productLocal = productLocal;
        this.productPrice = productPrice;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductLocal() {
        return productLocal;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "productName='" + productName + '\'' +
                ", ProductLocal='" + productLocal + '\'' +
                '}';
    }
}


