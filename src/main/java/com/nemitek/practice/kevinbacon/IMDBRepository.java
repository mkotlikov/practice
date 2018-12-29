package com.nemitek.practice.kevinbacon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemitek.practice.Practice;
import com.nemitek.practice.zip.Zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class IMDBRepository {
    private final HashMap<Actor, Set<Movie>> actorToMovies = new HashMap<>();
    private final Map<Movie, Set<Actor>> movieToActors;
    private final File kevinBaconDbFile = new File(Practice.class.getClassLoader().getResource("kevin_bacon_db/kevin_bacon.db").getFile());
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public IMDBRepository() {
        final List<StoredMovie> storedMovies = new BufferedReader(new StringReader(Zip.inMemoryUnzipFile(kevinBaconDbFile)))
                .lines()
                .map( movieJson ->
                        {
                            try {
                                return objectMapper.readValue(movieJson, StoredMovie.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return new StoredMovie("", Collections.emptyList());
                            }
                        }
                )
                .collect(Collectors.toList());

        movieToActors = storedMovies
                .stream()
                .collect(Collectors
                        .toMap(
                                storedMovie -> new Movie(storedMovie.title),
                                storedMovie -> storedMovie.actors
                                        .stream()
                                        .map(actor -> new Actor(actor))
                                        .collect(Collectors.toSet())
                        )
                );

        storedMovies
                .stream()
                .forEach(storedMovie -> {
                    final Movie movie = new Movie(storedMovie.title);
                    storedMovie.actors
                            .stream()
                            .forEach(actorString -> {
                                final Actor actor = new Actor(actorString);

                                if (!actorToMovies.containsKey(actor)) {
                                    final Set<Movie> movies = new HashSet<>();
                                    movies.add(movie);

                                    actorToMovies.put(actor, movies);
                                } else {
                                    final Set<Movie> movies = actorToMovies.get(actor);
                                    movies.add(movie);
                                }
                            });
                });
    }

    public Set<Actor> getActors(final Movie movie) {
        return movieToActors.getOrDefault(movie, new HashSet<>());
    }

    public Set<Movie> getMovies(final Actor actor) {
        return actorToMovies.getOrDefault(actor, new HashSet<>());
    }
}
