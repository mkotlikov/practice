package com.nemitek.practice.kevinbacon;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ActorsNotFoundException extends Exception {
    public final List<Actor> actorsNotFound;

    public ActorsNotFoundException(List<Actor> actorsNotFound) {
        this.actorsNotFound = Collections.unmodifiableList(actorsNotFound);
    }

    @Override
    public String toString() {
        final String isAre = actorsNotFound.size() > 1 ? "are" : "is";
        return "ActorsNotFoundException: " + this.actorsNotFound
                .stream()
                .map(actor -> "\"" + actor.name + "\"")
                .collect(Collectors.joining(", ")) + " " + isAre + " missing from the database.";
    }
}
