package com.hltt;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Manufacture")
public class Manufacture {
    @Id
    public String id;
    @Column(nullable = false, length = 128)
    public String name;
    public String location;
    public int employee;

    @OneToMany(mappedBy = "manufacture")
    private List<Phone> phoneList;

    public Manufacture() {
    }

    public Manufacture(String id, String name, String location, int employee) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        int phoneSize = 0;
        if(phoneList != null){
            phoneSize = phoneList.size();
        };
        return "Manufacture [id=" + id + ", name=" + name + ", location=" + location + ", employee=" + employee + ", phoneList=" + phoneSize + "]";
    }
}
