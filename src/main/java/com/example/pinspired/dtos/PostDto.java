package com.example.pinspired.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @PositiveOrZero(message = "Id must be positive or zero")
    private long id;

    @Positive(message = "User id must be positive")
    private long userId;

    @Size(max = 150, message = "Title must not exceed 150 characters")
    private String title;

    @NotNull(message = "Description must not be null")
    @NotBlank(message = "Description must not be blank")
    @Size(max = 50_000, message = "Description must not exceed 50_000 characters")
    private String description;

    @NotNull(message = "Image URL must not be null")
    private String imageUrl;


    private String videoUrl;

    private String topic;


    private boolean accessible;
    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
}
