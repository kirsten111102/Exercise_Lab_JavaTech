package com.hltt;

import jakarta.persistence.*;

@Entity
@Table(name="Phone")
public class Phone {
    @Id
    public String id;
    @Column(nullable = false, length = 128)
    public String name;
    @Column(nullable = false)
    public int price;
    @Column(nullable = false)
    public String color;
    public String country;
    public int quantity;

    @ManyToOne
    @JoinColumn(name="manufacture_id", referencedColumnName = "id",  nullable=false)
    private Manufacture manufacture;

    public Phone() {
    }

    public Phone(String id, String name, int price, String color, String country, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.country = country;
        this.quantity = quantity;
    }

    public Phone(String id, String name, int price, String color, String country, int quantity, Manufacture manufacture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.country = country;
        this.quantity = quantity;
        this.manufacture = manufacture;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Phone [id=" + id + ", name=" + name + ", price=" + price + " VND, color=" + color + ", country=" + country
                + ", quantity=" + quantity + ", manufacture=" + manufacture.getName() + "]";
    }
}
