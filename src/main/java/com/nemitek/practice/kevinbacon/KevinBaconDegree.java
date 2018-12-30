package com.nemitek.practice.kevinbacon;

import java.util.List;

public class KevinBaconDegree {
    public final Actor toActor;
    public final Actor fromActor;
    public final List<ActorInMovie> baconPath;
    public final int degree;
    public final boolean greaterThanSixDegrees;

    public KevinBaconDegree(
            final Actor toActor,
            final Actor fromActor,
            final List<ActorInMovie> baconPath,
            final boolean greaterThanSixDegrees
    ) {
        this.toActor = toActor;
        this.fromActor = fromActor;
        this.baconPath = baconPath;
        this.degree = baconPath.size();
        this.greaterThanSixDegrees = greaterThanSixDegrees;
    }

    public void printDegrees() {
        System.out.println("Actors: " + fromActor.name + " -> " + toActor.name);

        if (greaterThanSixDegrees) {
            System.out.println("Degrees: greater than 6!");
            return;
        }

        String pathDescription = fromActor.name;

        for (final ActorInMovie actorInMovie: baconPath) {
            pathDescription += " was in " + actorInMovie.movie.title + " with " + actorInMovie.actor.name + "\n-> " + actorInMovie.actor.name;
        }

        System.out.println("Degrees: " + degree);
        System.out.println("Path: " + pathDescription);
    }
}
