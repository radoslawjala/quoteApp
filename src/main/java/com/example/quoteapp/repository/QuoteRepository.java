package com.example.quoteapp.repository;

import com.example.quoteapp.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository  extends JpaRepository<Quote, Long> {
}
