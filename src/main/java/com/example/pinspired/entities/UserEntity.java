package com.example.pinspired.entities;


import jakarta.persistence.*;
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

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

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
    private boolean active;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postEntities;

}













