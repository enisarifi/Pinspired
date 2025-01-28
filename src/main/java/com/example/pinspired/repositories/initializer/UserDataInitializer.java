package com.example.pinspired.repositories.initializer;


import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer implements Runnable {

    private final UserRepository userRepository;
    // password encoder

    @Autowired
    public UserDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        if (userRepository.count() == 0) {
            UserEntity user1 = new UserEntity();
            user1.setName("John");
            user1.setSurname("Doe");
            user1.setEmail("john.doe@example.com");
            user1.setLocation("New York");
            user1.setBirthday("1990-01-01");
            user1.setBio("TEST");
            user1.setGender("M");
            user1.setPassword("123123123"); //password.encode("123123123")
            user1.setWebsite("https://example.com");
            user1.setProfilePicture("https://example.com/profile.jpg");
            userRepository.save(user1);
        }
    }
}