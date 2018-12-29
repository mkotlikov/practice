package com.nemitek.practice.kevinbacon;

import javafx.scene.control.ListCell;

import java.util.*;

public class KevinBaconDegreeFactory {
    private final IMDBRepository imdbRepository;

    public KevinBaconDegreeFactory(final IMDBRepository imdbRepository) {
        this.imdbRepository = imdbRepository;
    }

    public KevinBaconDegree getDegrees(final Actor actor) {
        final Actor kevinBacon = new Actor("Kevin Bacon");

        if (actor.equals(kevinBacon)) {
            return new KevinBaconDegree(
                    kevinBacon,
                    Collections.emptyList()
            );
        }

        final Set<Actor> visitedActors = new HashSet<>();
        final Set<Movie> visitedMovies = new HashSet<>();
        final LinkedList<ActorPath> traversalQueue = new LinkedList<>();
        ActorPath parentActorPath = new ActorPath(kevinBacon, null, null);
        int degrees = 1;
        while (!parentActorPath.actor.equals(actor) && degrees < 7) {
//            degrees++;
            if (parentActorPath.actor != null) {
                visitedActors.add(parentActorPath.actor);
            }
            if (parentActorPath.movie != null) {
                visitedMovies.add(parentActorPath.movie);
            }

            for (Movie movie: imdbRepository.getMovies(parentActorPath.actor)) {
                if (visitedMovies.contains(movie)) {
                    continue;
                }

                for (Actor nextActor: imdbRepository.getActors(movie)) {
                    if (visitedActors.contains(nextActor)) {
                        continue;
                    }

                    traversalQueue.add(
                            new ActorPath(
                                    nextActor,
                                    movie,
                                    parentActorPath
                            )
                    );
                }
            }

            if (!traversalQueue.isEmpty()) {
                parentActorPath = traversalQueue.removeFirst();
            } else {
                break;
            }
        }

        ActorPath reverseIterator = parentActorPath;
        final LinkedList<ActorInMovie> baconPath = new LinkedList();
        while(!reverseIterator.actor.equals(kevinBacon)) {
            baconPath.addFirst(new ActorInMovie(reverseIterator.actor, reverseIterator.movie));
            reverseIterator = reverseIterator.parentActorPath;
        }

        return new KevinBaconDegree(
                actor,
                baconPath
        );
    }
}
