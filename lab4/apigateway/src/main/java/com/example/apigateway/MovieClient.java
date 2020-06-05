package com.example.apigateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@FeignClient(value = "movie-service")
public interface MovieClient {
    @RequestMapping(path = "/config", method = RequestMethod.GET)
    ResponseEntity<?> GetConfig();

    @RequestMapping(path="/movie", method = RequestMethod.GET)
    ResponseEntity<?> GetAll();

    @RequestMapping(path="/movie", method = RequestMethod.POST)
    ResponseEntity<?> Add(@RequestBody Movie game) throws CustomException;

    @RequestMapping(path = "/movie/{id}", method = RequestMethod.GET)
    ResponseEntity<?> GetById(@PathVariable Integer id);

    @RequestMapping(path="/movie/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> Update(@PathVariable Integer id,
                                             @RequestBody Movie movie) throws ParseException;

    @RequestMapping(path="/movie/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> DeleteById(@PathVariable Integer id);
}
