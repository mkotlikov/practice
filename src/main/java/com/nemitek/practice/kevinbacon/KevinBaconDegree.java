package com.nemitek.practice.kevinbacon;

import java.util.List;

public class KevinBaconDegree {
    public final Actor actor;
    public final List<ActorInMovie> baconPath;
    public final int degree;

    public KevinBaconDegree(final Actor actor, final List<ActorInMovie> baconPath) {
        this.actor = actor;
        this.baconPath = baconPath;
        this.degree = baconPath.size();
    }
}
