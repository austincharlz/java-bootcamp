package com.academy.bank;

public class Customer implements Printable {
    private String customerId;
    private String name;
    private String email;
    private String phone;

    public Customer(String customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters with some validation
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId() {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be blank.");
        }
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }
    public void setName() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail() {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank.");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone() {
        if (phone == null || phone.length() != 10) {
            throw new IllegalArgumentException("Phone number must be 10 digits.");
        }
        this.phone = phone;
    }


    public void display() {
        System.out.printf("ID: %s%nName: %s%nEmail: %s%nPhone: %s%n", customerId, name, email, phone);
    }

    @Override
    public void printDetails() {
       display();
    }
}