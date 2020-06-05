package com.example.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
public class MovieController {

    @Autowired
    ProxyService _client;

    @Autowired
    ConfigClientAppConfiguration configClientAppConfiguration;

    @RequestMapping(path = "/config", method = RequestMethod.GET)
    public ResponseEntity<?> GetConfig() {
        HashMap<String, String> configmap = new HashMap<>();
        configmap.put("gateway prop1", configClientAppConfiguration.getProp1());
        configmap.put("gateway prop2", configClientAppConfiguration.getProp2());
        configmap.put("gateway prop3", configClientAppConfiguration.getProp3());
        configmap.put("gateway prop4", configClientAppConfiguration.getProp4());
        return ResponseEntity.ok(configmap);
    }

    @RequestMapping(path = "/movie", method = RequestMethod.GET)
    public ResponseEntity<?> GetAll() {
        try {
            return _client.GetAll();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/movie/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetById(@PathVariable Integer id) {
        try {
            return _client.GetById(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Movie wasn't found", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/movie", method = RequestMethod.POST)
    public ResponseEntity<?> Add(@RequestBody Movie movie) throws CustomException {
        try {
            return _client.Add(movie);
        } catch (CustomException e) {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/movie/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> DeleteById(@PathVariable int id) {
        try {
            return _client.DeleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Movie wasn't found", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/movie/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> Update(@RequestBody Movie movie, @PathVariable int id) throws CustomException {
        try {
            return _client.Update(id, movie);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }

}
