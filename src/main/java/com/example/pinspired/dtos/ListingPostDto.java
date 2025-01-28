package com.example.pinspired.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingPostDto {
    private long id;
    private long userId;
    private String fullName;
    private String content;
    private LocalDateTime postedAt;
    private String postedAgo;
}
