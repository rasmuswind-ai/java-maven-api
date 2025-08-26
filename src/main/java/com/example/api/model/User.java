package com.example.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "User entity representing a system user")
public class User {
    
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;
    
    @NotBlank(message = "Name cannot be blank")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;
    
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
    
    @NotNull(message = "Age cannot be null")
    @Schema(description = "Age of the user", example = "25")
    private Integer age;

    // Default constructor
    public User() {}

    // Constructor with all fields
    public User(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
