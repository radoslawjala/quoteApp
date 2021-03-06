package com.example.quoteapp.service;

import com.example.quoteapp.model.Quote;
import com.example.quoteapp.model.QuoteDto;
import com.example.quoteapp.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuoteService {

    private final QuoteRepository repository;

    public void addQuote(QuoteDto quoteDto) {
        Quote quote = new Quote(quoteDto.getAuthor(), quoteDto.getQuote());
        repository.save(quote);
    }

    public List<QuoteDto> getAllQuotes() {
        return repository
                .findAll()
                .stream()
                .map(quote -> new QuoteDto(quote.getAuthor(), quote.getQuote()))
                .collect(Collectors.toList());
    }

    public boolean update(long id, QuoteDto quoteDto) {
        Optional<Quote> quoteOptional = repository.findById(id);
        if (quoteOptional.isPresent()) {
            Quote quote = quoteOptional.get();
            quote.setQuote(quoteDto.getQuote());
            quote.setAuthor(quoteDto.getAuthor());
            repository.save(quote);
            return true;
        }
        return false;
    }

    public boolean delete(long id) {
        Optional<Quote> quote = repository.findById(id);
        if(quote.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
