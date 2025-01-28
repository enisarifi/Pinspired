package com.example.pinspired.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
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

}
