package com.example.quoteapp;

import com.example.quoteapp.controller.QuoteController;
import com.example.quoteapp.model.QuoteDto;
import com.example.quoteapp.repository.QuoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
class QuoteAppApplicationTests {

    @Autowired
    private QuoteController controller;
    @Autowired
    private QuoteRepository repository;
    private BindingResult bindingResult;
    private QuoteDto correctQuote = new QuoteDto("Thomas Jefferson", "When injustice becomes law, resistance becomes duty");

    @BeforeEach
    void setUp() {
        bindingResult = mock(BindingResult.class);
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    public void shouldAddNewQuoteSuccessfully() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        //when
        controller.add(correctQuote, bindingResult);
        //then
        List<QuoteDto> allQuotes = controller.getAllQuotes();
        assertThat(allQuotes.size()).isEqualTo(1);
        assertThat(allQuotes.get(0).getAuthor()).isEqualTo(correctQuote.getAuthor());
        assertThat(allQuotes.get(0).getQuote()).isEqualTo(correctQuote.getQuote());
    }

    @Test
    void shouldNotAddIncorrectQuote() {
        //given
        QuoteDto quote = new QuoteDto("T", "W");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        controller.add(quote, bindingResult);
        //then
        List<QuoteDto> allQuotes = controller.getAllQuotes();
        assertThat(allQuotes.size()).isEqualTo(0);
    }

    @Test
    void shouldUpdateQuoteCorrectly() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        controller.add(correctQuote, bindingResult);
        //when
        QuoteDto newQuote = new QuoteDto("John Mayer", "Gravity is working against me");
        controller.update(1, newQuote, bindingResult);
        //then
        assertThat(controller.getAllQuotes().get(0).getAuthor()).isEqualTo(newQuote.getAuthor());
        assertThat(controller.getAllQuotes().get(0).getQuote()).isEqualTo(newQuote.getQuote());
    }

    //    private void addExampleQuotes() {
//        Quote quote1 = new Quote("John Mayer", "Gravity is working against me");
//        Quote quote2 = new Quote("No es lo mismo ser que estar", "Alejandro Sanz");
//        Quote quote3 = new Quote("Chester Bennington", "When life leaves us blind, love keeps us kind");
//
//        repository.save(quote1);
//        repository.save(quote2);
//        repository.save(quote3);
//    }
}
