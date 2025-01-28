package com.example.pinspired.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto
{
    @PositiveOrZero(message = "Id must be positive or zero")
    private long id;

    @Positive(message = "User id must be positive")
    private long userId;

    @NotNull(message = "Content must not be null")
    @NotBlank(message = "Content must not be blank")
    @Size(max = 50_000, message = "Content must not exceed 50_000 characters")
    private String content;

    private String imageUrl;

    private String videoUrl;

    private boolean accessible;
    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
}
