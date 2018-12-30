package com.nemitek.practice.kevinbacon;

import java.util.*;

public class KevinBaconDegreeFactory {
    private final IMDBRepository imdbRepository;

    public KevinBaconDegreeFactory(final IMDBRepository imdbRepository) {
        this.imdbRepository = imdbRepository;
    }

    public KevinBaconDegree getDegrees(final Actor fromActor, final Actor toActor) throws ActorsNotFoundException {
        final int fromActorNumberOfMovies = this.imdbRepository.getMovies(fromActor).size();
        final int toActorNumberOfMovies = this.imdbRepository.getMovies(toActor).size();

        if (fromActorNumberOfMovies <= 0 && toActorNumberOfMovies <= 0) {
            throw new ActorsNotFoundException(Arrays.asList(fromActor, toActor));
        } else if (fromActorNumberOfMovies <= 0) {
            throw new ActorsNotFoundException(Collections.singletonList(fromActor));
        } else if (toActorNumberOfMovies <= 0) {
            throw new ActorsNotFoundException(Collections.singletonList(toActor));
        }

        if (fromActorNumberOfMovies <= toActorNumberOfMovies) {
            return breadthFirstSearch(fromActor, toActor);
        } else {
            final KevinBaconDegree degree = breadthFirstSearch(toActor, fromActor);
            Collections.reverse(degree.baconPath);
            final List<ActorInMovie> flippedPath = new LinkedList<>();
            for (int i = 0; i < degree.baconPath.size() - 1; i++) {
                flippedPath.add(new ActorInMovie(degree.baconPath.get(i + 1).actor, degree.baconPath.get(i).movie));
            }
            flippedPath.add(new ActorInMovie(toActor, degree.baconPath.get(degree.baconPath.size() - 1).movie));
            return new KevinBaconDegree(
                    degree.fromActor,
                    degree.toActor,
                    flippedPath,
                    degree.greaterThanSixDegrees
            );
        }
    }

    private KevinBaconDegree breadthFirstSearch(final Actor rootActor, final Actor leafActor) {
        if (rootActor.equals(leafActor)) {
            return new KevinBaconDegree(
                    leafActor,
                    rootActor,
                    Collections.emptyList(),
                    false
            );
        }

        final Set<Actor> visitedActors = new HashSet<>();
        final Set<Movie> visitedMovies = new HashSet<>();
        final LinkedList<ActorPath> traversalQueue = new LinkedList<>();
        ActorPath parentActorPath = new ActorPath(rootActor, null, null, 0);
        while (!parentActorPath.actor.equals(leafActor) && parentActorPath.degree < 7) {
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
                                    parentActorPath,
                                    parentActorPath.degree + 1
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
        if (parentActorPath.degree >= 7) {
            return new KevinBaconDegree(
                    leafActor,
                    rootActor,
                    Collections.emptyList(),
                    true
            );
        }
        final LinkedList<ActorInMovie> baconPath = new LinkedList();
        while(!reverseIterator.actor.equals(rootActor)) {
            baconPath.addFirst(new ActorInMovie(reverseIterator.actor, reverseIterator.movie));
            reverseIterator = reverseIterator.parentActorPath;
        }

        return new KevinBaconDegree(
                leafActor,
                rootActor,
                baconPath,
                false
        );
    }
}
