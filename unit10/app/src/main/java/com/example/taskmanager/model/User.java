package com.example.taskmanager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Table(name = "users")
@Entity
public class User {

    @Id @GeneratedValue
    long id;
    @NotBlank @Size(min = 6) 
    @Column(unique = true)
    String username;
    @NotBlank @Size(min = 12)
    String password;
    String role; // "USER", "ADMIN"


    public User() {} //Database needs this overload/signature for preload + reflection 

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}