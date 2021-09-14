package com.example.quoteapp.controller;

import com.example.quoteapp.model.MessageResponse;
import com.example.quoteapp.model.Quote;
import com.example.quoteapp.model.QuoteDto;
import com.example.quoteapp.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/quotes")
@Slf4j
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService service;

    @GetMapping
    public List<QuoteDto> getAllQuotes() {
        return service.getAllQuotes();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> add(@Valid @RequestBody QuoteDto quoteDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(error -> log.error(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new MessageResponse("Error while adding new quote"));
        } else {
            Quote quote = new Quote(quoteDto.getAuthor(), quoteDto.getQuote());
            service.addQuote(quote);
            log.info("Quote added successfully " + quote);
            return ResponseEntity.ok().body(new MessageResponse(
                    "Quote added successfully"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable long id, @RequestBody @Valid QuoteDto quoteDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(error -> log.error(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new MessageResponse("Error while updating new quote"));
        }

        boolean updatedSuccessfully = service.update(id, quoteDto);
        if (updatedSuccessfully) {
            log.info("Quote updated successfully " + quoteDto);

            return ResponseEntity.ok().body(new MessageResponse("Quote updated successfully"));
        } else {
            log.error("Quote with id = " + id + " doesn't exist");

            return ResponseEntity.badRequest().body(new MessageResponse("Quote with id = " + id + " doesn't exist"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable long id) {
        if (service.getQuote(id).isPresent()) {
            service.delete(id);
            log.info("Quote with id = " + id + " deleted successfully");
//todo move to service
            return ResponseEntity.ok().body(new MessageResponse(
                    "Quote deleted successfully"));
        } else {
            log.error("Quote with id = " + id + " doesn't exist");
            return ResponseEntity.badRequest().body(new MessageResponse("Quote with id = " + id + " doesn't exist"));
        }
    }
}
