package com.example.api.controller;

import com.example.api.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Apis for managing users")
public class UserController {
    
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Initialize with some sample data
    public UserController() {
        users.put(1L, new User(1L, "John Doe", "john.doe@example.com", 25));
        users.put(2L, new User(2L, "Jane Smith", "jane.smith@example.com", 30));
        idGenerator.set(3);
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = User.class)))
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = new ArrayList<>(users.values());
        return ResponseEntity.ok(userList);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to be retrieved", example = "1", required = true)
            @PathVariable Long id) {
        User user = users.get(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<User> createUser(
            @Parameter(description = "User object to be created", required = true)
            @Valid @RequestBody User user) {
        Long id = idGenerator.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

        @Operation(summary = "Update an existing user", description = "Modify details of an existing user")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Invalid input data")
        })
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(
                @Parameter(description = "ID of the user to update", example = "1", required = true)
                @PathVariable Long id,
                @Parameter(description = "Updated user object", required = true)
                @Valid @RequestBody User user) {
            if (users.containsKey(id)) {
                user.setId(id);
                users.put(id, user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    @Operation(summary = "Delete a user", description = "Remove a user from the system by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", example = "1")
            @PathVariable Long id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
