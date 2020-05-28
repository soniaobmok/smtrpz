package com.gateway.apigateway.Movie;

import com.gateway.apigateway.ConfigClientAppConfiguration;
import com.gateway.apigateway.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RefreshScope
@Controller
public class MovieController {
    @Autowired
    MovieClient client;

    @Autowired
    ConfigClientAppConfiguration configClientAppConfiguration;

    @RequestMapping(path = "/config", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getConfig() {
        HashMap<String, String> configmap = new HashMap<String, String>();
        configmap.put("gateway prop1", configClientAppConfiguration.getProp1());
        configmap.put("gateway prop2", configClientAppConfiguration.getProp2());
        configmap.put("gateway prop3", configClientAppConfiguration.getProp3());
        configmap.put("gateway prop4", configClientAppConfiguration.getProp4());
        configmap.putAll(client.getConfig());
        return configmap;
    }

    @RequestMapping(path="/movie", method = RequestMethod.POST)
    public @ResponseBody String add (@RequestBody Movie movie) throws ParseException {
        return client.add(movie);
    }

    @RequestMapping(path="/movie", method = RequestMethod.GET)
    public @ResponseBody Signature<Iterable<Movie>> getAll() {
        return client.getAll();
    }

    @RequestMapping(path = "/movie/{id}", method = RequestMethod.GET)
    public @ResponseBody Signature<Optional<Movie>> getById(@PathVariable int id) {
        return client.getById(id);
    }

    @RequestMapping(path="/movie/{id}", method = RequestMethod.PUT)
    public @ResponseBody String update(@PathVariable Integer id,
                                       @RequestBody Movie movie) throws ParseException {
        return client.customUpdate(id, movie);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String delete(@PathVariable Integer id) {
        return client.delete(id);
    }
}
