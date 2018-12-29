package com.nemitek.practice.kevinbacon;

import java.util.Objects;

public class Movie {
    final String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public Movie(final String title) {
        this.title = title;
    }
}
