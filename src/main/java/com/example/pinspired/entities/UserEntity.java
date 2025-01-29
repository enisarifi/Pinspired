package com.example.pinspired.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false, length = 100)
    private String password;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(max = 50, message = "Surname must be at most 50 characters")
    @Column(nullable = false, length = 50)
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    private String profilePicture;
    private String coverPicture;
    private String bio;
    private String location;
    private String website;
    private String birthdate;
    private String gender;

    @Column(nullable = false)
    private boolean active = true; // Default value for new users

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> postEntities;
}