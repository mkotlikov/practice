package com.nemitek.practice.maze;

public final class Maze2D {
    public final boolean[][] body;
    public final int rowLength;
    public final int columnWidth;
    public final MazeNode2D start;
    public final MazeNode2D end;

    public Maze2D(
            final boolean[][] body,
            final MazeNode2D start,
            final MazeNode2D end,
            final int rowLength,
            final int columnWidth
    ) {
        this.body = body;
        this.start = start;
        this.end = end;
        this.rowLength = rowLength;
        this.columnWidth = columnWidth;
    }
}
