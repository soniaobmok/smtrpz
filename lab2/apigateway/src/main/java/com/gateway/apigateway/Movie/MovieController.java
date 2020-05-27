package com.gateway.apigateway.Movie;

import com.gateway.apigateway.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Optional;

@Controller
@RequestMapping(path="/movie")
public class MovieController {
    @Autowired
    MovieClient client;

    @RequestMapping(path="", method = RequestMethod.POST)
    public @ResponseBody String add (@RequestBody Movie movie) throws ParseException {
        return client.add(movie);
    }

    @RequestMapping(path="", method = RequestMethod.GET)
    public @ResponseBody Signature<Iterable<Movie>> getAll() {
        return client.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Signature<Optional<Movie>> getById(@PathVariable int id) {
        return client.getById(id);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public @ResponseBody String update(@PathVariable Integer id,
                                       @RequestBody Movie movie) throws ParseException {
        return client.customUpdate(id, movie);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String delete(@PathVariable Integer id) {
        return client.delete(id);
    }
}
