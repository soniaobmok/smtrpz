package com.concert.eurekaclient.Movie;

import com.concert.eurekaclient.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;

@RefreshScope
@Controller
public class MovieController {
    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @Value("${test.prop5:}")
    private String prop5;

    @Value("${test.prop6:}")
    private String prop6;

    @Autowired
    private MovieRepository repository;

    @RequestMapping(path="/config", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, String> getConfig() {
        HashMap<String, String> configmap = new HashMap<String, String>();
        configmap.put("movie service prop5", prop5);
        configmap.put("movie service prop6", prop6);
        return configmap;
    }

    @RequestMapping(path="/movie", method = RequestMethod.POST)
    public @ResponseBody String add (@RequestBody Movie movie) throws ParseException {
        repository.save(movie);
        return "Saved";
    }

    @RequestMapping(path="/movie", method = RequestMethod.GET)
    public @ResponseBody Signature<Iterable<Movie>> getAll() {
        return new Signature<Iterable<Movie>>(repository.findAll(), instanceId);
    }

    @RequestMapping(path="/movie/{id}", method = RequestMethod.GET)
    public @ResponseBody Signature<Optional<Movie>> getById(@PathVariable int id) {
        return new Signature<Optional<Movie>>(repository.findById(id), instanceId);
    }

    @RequestMapping(path="/movie/{id}", method = RequestMethod.PUT)
    public @ResponseBody String update(@PathVariable Integer id,
                                       @RequestBody Movie movie) throws ParseException {
        repository.customUpdate(id, movie.getTitle(),
                (new SimpleDateFormat("dd/MM/yyyy")).parse(movie.getDate()),
                movie.getGenre(), movie.getCompany(),
                movie.getRating(), movie.getDuration());
        return "Updated";
    }

    @RequestMapping(path="/movie/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String delete(@PathVariable Integer id) {
        Movie instance  = repository.find(id);
        repository.delete(instance);
        return "Deleted";
    }
}
