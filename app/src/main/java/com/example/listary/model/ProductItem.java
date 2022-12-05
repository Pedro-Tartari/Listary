package com.example.listary.model;

public class ProductItem {

    private String productName, productLocal, productBrand;
    private double productPrice, productQuantity;
    private double productTotalPrice;
    private String id;

    public ProductItem() {
    }

    public ProductItem(String productName, String productLocal, String productBrand, double productPrice, String id) {
        this.productName = productName;
        this.productLocal = productLocal;
        this.productPrice = productPrice;
        this.id = id;
        this.productBrand = productBrand;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getId() {
        return id;
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

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "ProductName= " + productName +
                "ProductLocal= " + productLocal +
                "ProductQuantity= " + productQuantity +
                "ProductPrice= " + productPrice;
    }
}


