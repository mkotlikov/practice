package com.nemitek.practice.kevinbacon;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class StoredMovie {
    public final String title;
    public final List<String> actors;

    public String getTitle() {
        return title;
    }

    public List<String> getActors() {
        return actors;
    }

    public StoredMovie(
            @JsonProperty("title") final String title,
            @JsonProperty("cast") final List<String> actors
    ) {
        this.title = title;
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoredMovie storedMovie = (StoredMovie) o;
        return Objects.equals(title, storedMovie.title) &&
                Objects.equals(actors, storedMovie.actors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, actors);
    }
}
