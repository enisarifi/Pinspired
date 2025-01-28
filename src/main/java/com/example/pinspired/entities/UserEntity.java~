package dev.naimsulejmani.gr1facebook.entities;


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

    @Column(nullable = false, length = 50)
    private String name;
    private String surname;
    private String email;
    private String password;
    private String profilePicture;
    private String coverPicture;
    private String bio;
    private String location;
    private String website;
    private String birthday;
    private String gender;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postEntities;

}













