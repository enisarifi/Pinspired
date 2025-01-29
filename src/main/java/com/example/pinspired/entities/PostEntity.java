package com.example.pinspired.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Content is required")
    @Size(max = 50_000, message = "Content must be at most 50,000 characters")
    @Column(nullable = false, length = 50_000)
    private String content;

    @Size(max = 500, message = "Image URL must be at most 500 characters")
    @Column(length = 500)
    private String imageUrl;

    @Size(max = 500, message = "Video URL must be at most 500 characters")
    @Column(length = 500)
    private String videoUrl;

    private boolean accessible = true; // Default value for new posts

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}