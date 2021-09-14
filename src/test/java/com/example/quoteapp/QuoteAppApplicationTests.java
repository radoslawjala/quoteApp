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
    private final QuoteDto correctQuote = new QuoteDto("Thomas Jefferson", "When injustice becomes law, resistance becomes duty");

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
        QuoteDto newQuote = new QuoteDto("John Mayer", "Gravity is working against me");
        //when
        long updatedQuoteId = repository.findAll().stream().findFirst().get().getId();
        controller.update(updatedQuoteId, newQuote, bindingResult);
        //then
        assertThat(controller.getAllQuotes().get(0).getAuthor()).isEqualTo(newQuote.getAuthor());
        assertThat(controller.getAllQuotes().get(0).getQuote()).isEqualTo(newQuote.getQuote());
    }

    @Test
    void shouldNotUpdateIncorrectQuote() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        controller.add(correctQuote, bindingResult);
        QuoteDto newQuote = new QuoteDto("J", "");
        //when
        long id = repository.findAll().stream().findFirst().get().getId();
        given(bindingResult.hasErrors()).willReturn(true);
        controller.update(id, newQuote, bindingResult);
        //then
        assertThat(controller.getAllQuotes().get(0).getAuthor()).isNotEqualTo(newQuote.getAuthor());
        assertThat(controller.getAllQuotes().get(0).getQuote()).isNotEqualTo(newQuote.getQuote());

    }

    @Test
    void shouldDeleteQuoteCorrectly() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        controller.add(correctQuote, bindingResult);
        //when
        long id = repository.findAll().stream().findFirst().get().getId();
        controller.delete(id);
        //then
        assertThat(controller.getAllQuotes().size()).isEqualTo(0);
    }

    @Test
    void shouldNotDeleteQuoteWithIncorrectID() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        controller.add(correctQuote, bindingResult);
        //when
        long id = repository.findAll().stream().findFirst().get().getId() + 1;
        controller.delete(id);
        //then
        assertThat(controller.getAllQuotes().size()).isEqualTo(1);
    }
}
