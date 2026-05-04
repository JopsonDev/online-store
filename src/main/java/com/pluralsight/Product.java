package com.pluralsight;

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;


    public Product(String id, String name, double price, int quantiy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setQuantity(int quantity){this.quantity = quantity;}

    @Override
    public String toString() {
        return  "Quantity: " + quantity + ") Id: " + id +
                ", Name: " + name + '\'' +
                ", Price: " + price;
    }
}
