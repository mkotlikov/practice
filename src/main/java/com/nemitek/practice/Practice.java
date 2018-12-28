package com.nemitek.practice;

import com.nemitek.practice.maze.Maze2D;
import com.nemitek.practice.maze.MazeFactory;
import com.nemitek.practice.maze.MazeNode2D;
import com.nemitek.practice.maze.MazeSolver;

import java.util.HashSet;

public final class Practice {
    public static void main(String[] args) {
        final Maze2D maze = MazeFactory.build(100, 100);
        final HashSet<MazeNode2D> nodesInPath = MazeSolver.breadthFirstSearch(maze);
        MazeFactory.printHtmlMaze(maze, nodesInPath);
    }
}
