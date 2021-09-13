package com.example.quoteapp.service;

import com.example.quoteapp.model.Quote;
import com.example.quoteapp.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuoteService {

    private final QuoteRepository repository;

    public void addQuote(Quote quote) {
        repository.save(quote);
    }

    public List<Quote> getAllQuotes() {
        return repository.findAll();
    }

    public Optional<Quote> getQuote(long id) {
        return repository.findById(id);

    }

    public void update(Quote quote) {
        repository.save(quote);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
