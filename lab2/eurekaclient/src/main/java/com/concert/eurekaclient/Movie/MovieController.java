package com.concert.eurekaclient.Movie;

import com.concert.eurekaclient.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
@RequestMapping(path="/movie")
public class MovieController {

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @Autowired
    private MovieRepository repository;

    @RequestMapping(path="", method = RequestMethod.POST)
    public @ResponseBody String add (@RequestBody Movie movie) throws ParseException {
        repository.save(movie);
        return "Saved";
    }

    @RequestMapping(path="", method = RequestMethod.GET)
    public @ResponseBody Signature<Iterable<Movie>> getAll() {
        return new Signature<Iterable<Movie>>(repository.findAll(), instanceId);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public @ResponseBody Signature<Optional<Movie>> getById(@PathVariable int id) {
        return new Signature<Optional<Movie>>(repository.findById(id), instanceId);
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
    public @ResponseBody String delete(@PathVariable Integer id) {
        Movie instance  = repository.find(id);
        repository.delete(instance);
        return "Deleted";
    }
}
