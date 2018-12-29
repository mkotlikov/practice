package com.nemitek.practice;

import com.nemitek.practice.kevinbacon.Actor;
import com.nemitek.practice.kevinbacon.IMDBRepository;
import com.nemitek.practice.kevinbacon.KevinBaconDegree;
import com.nemitek.practice.kevinbacon.KevinBaconDegreeFactory;
import com.nemitek.practice.maze.Maze2D;
import com.nemitek.practice.maze.MazeFactory;
import com.nemitek.practice.maze.MazeNode2D;
import com.nemitek.practice.maze.MazeSolver;
import java.util.HashSet;

public final class Practice {
    public static void main(String[] args) {
        final KevinBaconDegreeFactory kbdFactory = new KevinBaconDegreeFactory(new IMDBRepository());
//        final KevinBaconDegree kbDegree = kbdFactory.getDegrees(new Actor("Kevin Bacon"));
        final KevinBaconDegree kbDegree = kbdFactory.getDegrees(new Actor("Julia Stiles"));
//        final KevinBaconDegree kbDegree = kbdFactory.getDegrees(new Actor("Mena Suvari"));

        final Maze2D maze = MazeFactory.build(100, 100);
        final HashSet<MazeNode2D> nodesInPath = MazeSolver.breadthFirstSearch(maze);
        MazeFactory.printHtmlMaze(maze, nodesInPath);
    }
}
