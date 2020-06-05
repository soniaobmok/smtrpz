package com.example.apigateway;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;


@Component
public class ProxyService {
    private static final String BACKEND_A = "movie-service";

    @Autowired
    private MovieClient _client;

    @CircuitBreaker(name = BACKEND_A, fallbackMethod = "fallback")
    public ResponseEntity<?> GetAll() {
        return _client.GetAll();
    }

    @CircuitBreaker(name = BACKEND_A, fallbackMethod = "fallback")
    public ResponseEntity<?> GetById(@PathVariable Integer id) {
        return _client.GetById(id);
    }

    @Retry(name = BACKEND_A)
    public ResponseEntity<?> Add(@RequestBody Movie game) throws CustomException {
        try {
            return _client.Add(game);
        } catch (CustomException e) {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> DeleteById(@PathVariable int id) {
        try {
            return _client.DeleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Movie wasn't found", HttpStatus.BAD_REQUEST);
        }
    }

    @Retry(name = BACKEND_A)
    ResponseEntity<?> Update(@PathVariable int id, @RequestBody Movie movie) {
        try {
            return _client.Update(id, movie);
        } catch (ParseException e) {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> fallback(Throwable e) {
        return new ResponseEntity<>(new ArrayList<Movie>(),HttpStatus.OK);
    }
}
