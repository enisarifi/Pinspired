package com.example.pinspired.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false, length = 50_000)
    private String content;

    @Column(length = 500)
    private String imageUrl;
    @Column(length = 500)
    private String videoUrl;

    private boolean accessible;
//    private boolean deleted;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}















