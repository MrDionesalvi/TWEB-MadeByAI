package com.biblioshare.dto;

import com.biblioshare.entity.BookCondition;
import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String author;
    private String isbn;
    private Long genreId;
    private String description;
    private BookCondition condition;
    private Boolean availableForLoan;
}
