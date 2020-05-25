package com.concert.eurekaclient.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
@RequestMapping(path="/movie")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @RequestMapping(path="", method = RequestMethod.POST)
    public @ResponseBody String add (@RequestBody Movie movie) throws ParseException {
        repository.save(movie);
        return "Saved";
    }

    @RequestMapping(path="", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Movie> getAll() {
        return repository.findAll();
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Movie> getById(@PathVariable int id) {
        return repository.findById(id);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public @ResponseBody String update(@PathVariable Integer id,
                                       @RequestBody Movie movie) throws ParseException {
        repository.customUpdate(id, movie.getTitle(),
                (new SimpleDateFormat("dd/MM/yyyy")).parse(movie.getDate()),
                movie.getGenre(), movie.getCompany(),
                movie.getRating(), movie.getDuration());
        return "Updated";
    }

    @RequestMapping(path="/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void delete(@PathVariable Integer id) {
        Movie instance  = repository.find(id);
        repository.delete(instance);
    }

    @RequestMapping(path="/find/{title}", method = RequestMethod.GET)
    public @ResponseBody Movie findByTitle(@PathVariable String title) {
        return repository.findByTitle(title);
    }
}
