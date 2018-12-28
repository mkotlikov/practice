package com.nemitek.practice.maze;

public class MazeNode2DPath {
    public final MazeNode2D node;
    public final MazeNode2DPath previousNode;

    public MazeNode2DPath(
            final MazeNode2D node,
            final MazeNode2DPath previousNode
    ) {
        this.node = node;
        this.previousNode = previousNode;
    }
}
