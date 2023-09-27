package com.hltt;

public class Product {
    int id;
    String name;
    double price;
    String color;
    int remaining;

    public Product(){

    }

    public Product(String name, double price, String color, int remaining) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.remaining = remaining;
    }
    
    public Product(int id, String name, double price, String color, int remaining) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.remaining = remaining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=$" + price +
                ", color='" + color + '\'' +
                ", remaining=" + remaining +
                '}';
    }
}
