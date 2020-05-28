package com.gateway.apigateway.Movie;

import com.gateway.apigateway.Signature;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

@FeignClient(value = "movie-service")
public interface MovieClient {
    @RequestMapping(path = "/config", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getConfig();

    @RequestMapping(path="/movie", method = RequestMethod.POST)
    public @ResponseBody String add (@RequestBody Movie movie) throws ParseException;

    @RequestMapping(path="/movie", method = RequestMethod.GET)
    public @ResponseBody Signature<Iterable<Movie>> getAll();

    @RequestMapping(path = "/movie/{id}", method = RequestMethod.GET)
    public @ResponseBody Signature<Optional<Movie>> getById(@PathVariable int id);

    @RequestMapping(path="/movie/{id}", method = RequestMethod.PUT)
    public @ResponseBody String customUpdate(@PathVariable Integer id,
                                             @RequestBody Movie movie) throws ParseException;

    @RequestMapping(path="/movie/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String  delete(@PathVariable Integer id);
}