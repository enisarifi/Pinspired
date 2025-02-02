package com.example.pinspired.repositories.initializer;

import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize() {
        if (userRepository.count() == 0) {
            UserEntity user1 = new UserEntity();
            user1.setName("Enis");
            user1.setSurname("Arifi");
            user1.setUsername("rainman");
            user1.setEmail("enis@gmail.com");
            user1.setLocation("Podujeva");
            user1.setBirthdate("2005-18-01");
            user1.setBio("TEST");
            user1.setGender("M");
            user1.setActive(true);
            user1.setPassword(passwordEncoder.encode("Shpejt123")); // Hashed password
            user1.setWebsite("https://www.youtube.com");
            user1.setProfilePicture("/images/icons/no_pfp.png");
            userRepository.save(user1);
        }
    }
}