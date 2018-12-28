package com.nemitek.practice.maze;

import java.util.Objects;

public final class MazeNode2D {
    public final int x;
    public final int y;

    public MazeNode2D(
            final int x,
            final int y
    ) {
        this.x = x;
        this.y = y;
    }

    public MazeNode2D moveDown() {
        return new MazeNode2D(
                x + 1,
                y
        );
    }

    public MazeNode2D moveUp() {
        return new MazeNode2D(
                x - 1,
                y
        );
    }

    public MazeNode2D moveRight() {
        return new MazeNode2D(
                x,
                y + 1
        );
    }

    public MazeNode2D moveLeft() {
        return new MazeNode2D(
                x,
                y - 1
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeNode2D that = (MazeNode2D) o;
        return x == that.x &&
                y == that.y;
    }
}
