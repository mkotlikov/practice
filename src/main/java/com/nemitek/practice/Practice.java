package com.nemitek.practice;

import com.nemitek.practice.kevinbacon.*;
import com.nemitek.practice.maze.Maze2D;
import com.nemitek.practice.maze.MazeFactory;
import com.nemitek.practice.maze.MazeNode2D;
import com.nemitek.practice.maze.MazeSolver;
import java.util.HashSet;

public final class Practice {
    public static void main(String[] args) {
//        baconNumbers();
//        mazeMakerAndSolver();
    }

    public static void mazeMakerAndSolver() {
        final Maze2D maze = MazeFactory.build(100, 100);
        final HashSet<MazeNode2D> nodesInPath = MazeSolver.breadthFirstSearch(maze);
        MazeFactory.printHtmlMaze(maze, nodesInPath);
    }

    public static void baconNumbers() {
        final KevinBaconDegreeFactory kbdFactory = new KevinBaconDegreeFactory(new IMDBRepository());

        baconNumber(kbdFactory, new Actor("William Rufus Shafter"), new Actor("Kevin Bacon"));
        baconNumber(kbdFactory, new Actor("Kevin Bacon"), new Actor("William Rufus Shafter"));
        baconNumber(kbdFactory, new Actor("Kevin Bacon"), new Actor("Kevin Bacon"));
        baconNumber(kbdFactory, new Actor("Kevin Bacon"), new Actor("Zippy McGee"));
        baconNumber(kbdFactory, new Actor("Zippy McGee"), new Actor("Kevin Bacon"));
    }

    private static void baconNumber(final KevinBaconDegreeFactory kbdFactory, final Actor fromActor, final Actor toActor) {
        System.out.println("-------------------------------------------------------------------------");
        try {
            final KevinBaconDegree kbDegree = kbdFactory.getDegrees(fromActor, toActor);
            kbDegree.printDegrees();
        } catch (ActorsNotFoundException e) {
            System.out.println(e);
        }
        System.out.println("-------------------------------------------------------------------------");
    }
}
