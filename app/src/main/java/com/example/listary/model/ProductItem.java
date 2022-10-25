package com.example.listary.model;

public class ProductItem {

    private String productName, ProductLocal;

    public ProductItem(String productName, String productLocal) {
        this.productName = productName;
        ProductLocal = productLocal;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductLocal() {
        return ProductLocal;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "productName='" + productName + '\'' +
                ", ProductLocal='" + ProductLocal + '\'' +
                '}';
    }
}


