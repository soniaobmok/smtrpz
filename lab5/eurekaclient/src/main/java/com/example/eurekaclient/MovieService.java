package com.example.eurekaclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public Movie Add(Movie movie) throws CustomException {
        SanityChecks(movie);
        return repository.save(movie);
    }

    public void DeleteById(Integer id) throws CustomException {
        Movie existing = repository.findById(id).orElseThrow(() -> new CustomException("Movie wasn't found"));
        if (existing.isDeleted()) throw new CustomException("Movie was deleted");
        existing.setDeleted(true);
        repository.save(existing);
    }

    public Movie Update(Integer id, Movie movie) throws CustomException, ParseException {
        repository.customUpdate(id, movie.getTitle(),
                (new SimpleDateFormat("dd/MM/yyyy")).parse(movie.getDate()),
                movie.getGenre(), movie.getCompany(),
                movie.getRating(), movie.getDuration());
        return movie;
    }

    private void SanityChecks(Movie item) throws CustomException {
        if (item.getTitle() == null || item.getGenre() == null || item.getRating() == null ||
                item.getDate() == null || item.getCompany()== null || item.getDuration() == 0.0)
            throw new CustomException("Incorrect data");
    }

    public ResponseModel<List<Movie>> GetAll(String id) {
        List<Movie> list = new ArrayList<>();
        repository.findAll().forEach(list::add);

        List<Movie> filtered = new ArrayList<>();
        for (Movie item : list) {
            if (!item.isDeleted()) {
                filtered.add(item);
            }
        }
        return new ResponseModel<>(filtered, id);
    }

    public ResponseModel<Movie> GetById(Integer id, String str) throws CustomException {
        Movie res = repository.findById(id).orElseThrow(() -> new CustomException("Movie wasn't found"));
        if (res == null | res.isDeleted()) throw new CustomException("Movie was deleted");
        return new ResponseModel<>(res, str);
    }
}