package com.example.listary.model;

public class Product {

    private String name;
    private String brand;
    private String location;
    private double price;
    private int id;

    public Product() {
    }

    public Product(ProductBuilder productBuilder) {
        this.name = productBuilder.productName;
        this.brand = productBuilder.productBrand;
        this.location = productBuilder.productLocation;
        this.price = productBuilder.productPrice;
        this.id = productBuilder.productInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductBrand() {
        return brand;
    }

    public void setProductBrand(String productBrand) {
        this.brand = productBrand;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class ProductBuilder {

        private final String productName;
        private String productBrand;
        private String productLocation;
        private final double productPrice;
        private final int productInt;

        public ProductBuilder(String productName, double productPrice, int productInt) {
            this.productName = productName;
            this.productPrice = productPrice;
            this.productInt = productInt;
        }

        public ProductBuilder selectBrand(String productBrand){
            this.productBrand = productBrand;
            return this;
        }

        public ProductBuilder selectLocation(String productLocation){
            this.productLocation = productLocation;
            return this;
        }

        public Product build(){
            Product product = new Product(this);
            return product;

        }
    }
}
