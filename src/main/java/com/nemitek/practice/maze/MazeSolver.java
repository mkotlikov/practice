package com.nemitek.practice.maze;

import java.util.HashSet;
import java.util.LinkedList;

public class MazeSolver {
//    algorithm BreadthFirst(root) {
//        Pre: root is the root node of the BST
//        Post: the nodes in the BST have been visited in breadth first order
//        q <- queue
//        while (root != null) {
//            yield root.Value
//            if (root.Left != null) {
//                q.Enqueue(root.Left)
//            }
//            if (root.Right != null) {
//                q.Enqueue(root.Right)
//            }
//            if (!q.IsEmpty()) {
//                root <- q.Dequeue()
//            } else {
//                root <- null
//            }
//        }
//    }
    public static HashSet<MazeNode2D> breadthFirstSearch(final Maze2D maze) {
        MazeNode2DPath parentNode = new MazeNode2DPath(maze.start, null);
        final HashSet<MazeNode2D> visited = new HashSet<>();
        final LinkedList<MazeNode2DPath> nodeTraversalQueue = new LinkedList<>();

        while (!parentNode.node.equals(maze.end)) {
            visited.add(parentNode.node);

            moveNodes(visited, maze, parentNode.node.moveUp(), nodeTraversalQueue, parentNode);
            moveNodes(visited, maze, parentNode.node.moveDown(), nodeTraversalQueue, parentNode);
            moveNodes(visited, maze, parentNode.node.moveLeft(), nodeTraversalQueue, parentNode);
            moveNodes(visited, maze, parentNode.node.moveRight(), nodeTraversalQueue, parentNode);

            if (!nodeTraversalQueue.isEmpty()) {
                parentNode = nodeTraversalQueue.removeFirst();
            } else {
                break;
            }
        }

        final HashSet<MazeNode2D> nodesInPath = new HashSet<>();
        while (!parentNode.node.equals(maze.start)) {
            nodesInPath.add(parentNode.node);
           parentNode = parentNode.previousNode;
        }

        return nodesInPath;
    }

    private static void moveNodes(
            final HashSet<MazeNode2D> visited,
            final Maze2D maze,
            final MazeNode2D nextNode,
            final LinkedList<MazeNode2DPath> nodeTraversalQueue,
            final MazeNode2DPath parentNode
    ) {
        if (isMoveable(maze, nextNode) && !visited.contains(nextNode)) {
            nodeTraversalQueue.add(new MazeNode2DPath(nextNode, parentNode));
        }
    }

    private static boolean isMoveable(final Maze2D maze, final MazeNode2D newPosition) {
        if (newPosition.x >= 0 && newPosition.x < maze.rowLength && newPosition.y >= 0 && newPosition.y < maze.columnWidth && maze.body[newPosition.x][newPosition.y]) {
            return true;
        }

        return false;
    }
}
