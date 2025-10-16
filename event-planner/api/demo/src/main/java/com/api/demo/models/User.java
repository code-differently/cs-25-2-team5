package com.api.demo.models;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    // Constructor
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Example usage
    public static void main(String[] args) {
        User user = new User(1, "John Doe", "john@example.com", "password123");

        // Access fields
        System.out.println("User created: " + user.getName() + " (" + user.getEmail() + ")");

        // Update fields
        user.setName("Johnathan Doe");
        user.setPassword("newSecret456");

        System.out.println("Updated Name: " + user.getName());
        System.out.println("Updated Password: " + user.getPassword());
    }
}