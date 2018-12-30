package com.nemitek.practice.kevinbacon;

public class ActorPath {
    public final Actor actor;
    public final Movie movie;
    public final ActorPath parentActorPath;
    public final int degree;

    public ActorPath(final Actor actor, final Movie movie, final ActorPath parentActorPath, final int degree) {
        this.actor = actor;
        this.movie = movie;
        this.parentActorPath = parentActorPath;
        this.degree = degree;
    }
}
