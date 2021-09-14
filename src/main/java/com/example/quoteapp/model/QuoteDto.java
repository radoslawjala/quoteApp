package com.example.quoteapp.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class QuoteDto {

    @NotBlank(message = "The author cannot be empty")
    @Size(min = 3, max = 100, message = "Author should have 3-100 characters")
    private String author;
    @NotBlank(message = "The quote cannot be empty")
    @Size(min = 3, max = 250, message = "Quote should have 3-250 characters")
    private String quote;
}
