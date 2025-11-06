package com.biblioshare.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long bookId;
    private Integer rating;
    private String comment;
}
