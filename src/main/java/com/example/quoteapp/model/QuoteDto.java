package com.example.quoteapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class QuoteDto {

    @NotBlank(message = "The quote cannot be empty!")
    @Size(min = 3, max = 250, message = "The length of the quote must be between 3 and 250 characters.")
    private String author;
    @NotBlank(message = "The author cannot be empty!")
    @Size(min = 3, max = 100, message = "The length of the author must be between 3 and 100 characters.")
    private String quote;
}
