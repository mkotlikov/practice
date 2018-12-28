package com.nemitek.practice.maze;

import java.util.HashSet;
import java.util.Random;

public final class MazeFactory {

    public static void build(
            final int rowLength,
            final int columnWidth
    ) {
        final boolean[][] body = new boolean[rowLength][columnWidth];

        // Initialize to false
        for (int row = 0; row < rowLength; row++) {
            for (int column = 0; column < columnWidth; column++) {
                body[row][column] = false;
            }
        }

        final MazeNode2D start = buildRandomMazeNode2DEdge(
                rowLength,
                columnWidth
        );

        MazeNode2D end = null;
        while (end == null || start.equals(end) || start.x == end.x || start.y == end.y) {
            end = buildRandomMazeNode2DEdge(
                    rowLength,
                    columnWidth
            );
        }

        body[start.x][start.y] = true;
        body[end.x][end.y] = true;

        final Maze2D maze = new Maze2D(
                body,
                start,
                end,
                rowLength,
                columnWidth
        );

        buildPaths(
                new Random(),
                maze
        );

        printHtmlMaze(maze);
    }

    private static void buildPaths(
            final Random random,
            final Maze2D maze
    ) {
        while (!isMazeSolvable(maze)) {
            for (int i = 0; i < maze.rowLength; i++) {
                for (int j = 0; j < maze.columnWidth; j++) {
                    final MazeNode2D position = new MazeNode2D(i, j);

                    if (!maze.body[position.x][position.y]) {
                        continue;
                    }

                    if (isMoveableUp(maze, position) && random.nextBoolean()) {
                        final MazeNode2D newPosition = position.moveUp();
                        maze.body[newPosition.x][newPosition.y] = true;
                        continue;
                    }

                    if (isMoveableDown(maze, position) && random.nextBoolean()) {
                        final MazeNode2D newPosition = position.moveDown();
                        maze.body[newPosition.x][newPosition.y] = true;
                        continue;
                    }

                    if (isMoveableLeft(maze, position) && random.nextBoolean()) {
                        final MazeNode2D newPosition = position.moveLeft();
                        maze.body[newPosition.x][newPosition.y] = true;
                        continue;
                    }

                    if (isMoveableRight(maze, position) && random.nextBoolean()) {
                        final MazeNode2D newPosition = position.moveRight();
                        maze.body[newPosition.x][newPosition.y] = true;
                        continue;
                    }
                }
            }
        }
    }

    private static MazeNode2D buildRandomMazeNode2DEdge(
            final int rowLength,
            final int columnWidth
    ) {
        final Random randomNum = new Random();
        int x = randomNum.nextInt(rowLength);
        int y = 0;
        if (x == 0 || x == (rowLength - 1)) {
            y = 1 + randomNum.nextInt(columnWidth - 1);
        } else {
            final int leftOrRight = randomNum.nextInt(2);
            if (leftOrRight == 0) {
                y = 0;
            } else {
                y = columnWidth - 1;
            }
        }

        return new MazeNode2D(
                x,
                y
        );
    }

    private static boolean isValidNode(final Maze2D maze, final MazeNode2D position) {
        if (position.x < 0 || position.y < 0) {
            return false;
        }

        if (maze.rowLength <= position.x || maze.columnWidth <= position.y) {
            return false;
        }

        if (!maze.body[position.x][position.y]) {
            return false;
        }

        return true;
    }

    private static boolean isMazeSolvable(final Maze2D maze) {
        final HashSet<MazeNode2D> visited = new HashSet<>();
        visited.add(maze.start);

        int lastSize = 1;
        while (true) {
            final HashSet<MazeNode2D> visitedTemp = new HashSet<>();

            visited.stream()
                    .forEach(node -> {
                        final MazeNode2D up = node.moveUp();
                        if (isValidNode(maze, up)) {
                            visitedTemp.add(up);
                        }

                        final MazeNode2D down = node.moveDown();
                        if (isValidNode(maze, down)) {
                            visitedTemp.add(down);
                        }

                        final MazeNode2D left = node.moveLeft();
                        if (isValidNode(maze, left)) {
                            visitedTemp.add(left);
                        }

                        final MazeNode2D right = node.moveRight();
                        if (isValidNode(maze, right)) {
                            visitedTemp.add(right);
                        }
                    });

            visited.addAll(visitedTemp);

            if (visited.contains(maze.end)) {
                return true;
            }

            if (!visited.contains(maze.end) && lastSize == visited.size()) {
                return false;
            }

            lastSize = visited.size();
        }
    }

    private static boolean isMoveableLeft(final Maze2D maze, final MazeNode2D position) {
        if (position.y <= 0 || position.x == 0 || position.x == maze.rowLength - 1 || maze.body[position.x - 1][position.y - 1] || maze.body[position.x + 1][position.y - 1]) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isMoveableRight(final Maze2D maze, final MazeNode2D position) {
        if (position.y >= maze.columnWidth - 1 || position.x == 0 || position.x == maze.rowLength - 1 || maze.body[position.x - 1][position.y + 1] || maze.body[position.x + 1][position.y + 1]) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isMoveableUp(final Maze2D maze, final MazeNode2D position) {
        if (position.x <= 0 || position.y == 0 || position.y == maze.columnWidth - 1 || maze.body[position.x - 1][position.y - 1] || maze.body[position.x - 1][position.y + 1]) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isMoveableDown(final Maze2D maze, final MazeNode2D position) {
        if (position.x >= maze.rowLength - 1 || position.y == 0 || position.y == maze.columnWidth - 1 || maze.body[position.x + 1][position.y - 1] || maze.body[position.x + 1][position.y + 1]) {
            return false;
        } else {
            return true;
        }
    }

    public static void printMaze(final Maze2D maze) {
        System.out.println();
        for (int row = 0; row < maze.body.length; row++) {
            for (int column = 0; column < maze.body[0].length; column++) {
                if (maze.start.x == row && maze.start.y == column) {
                    System.out.print("S ");
                } else if (maze.end.x == row && maze.end.y == column) {
                    System.out.print("E ");
                } else if (maze.body[row][column]) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    public static void printHtmlMaze(final Maze2D maze) {
        final String gridDimension = "20px";
        final String heightWidth = "height:" + gridDimension + ";width:" + gridDimension + ";";

        String columns = "";
        for (int i = 0; i < maze.body[0].length; i++) {
            columns += gridDimension + " ";
        }

        String divs = "";
        for (int row = 0; row < maze.body.length; row++) {
            for (int column = 0; column < maze.body[0].length; column++) {
                if (maze.start.x == row && maze.start.y == column) {
                    divs += "  <div style=\"" + heightWidth + "background-color: rgba(0, 255, 0, 1);\"></div>\n";
                } else if (maze.end.x == row && maze.end.y == column) {
                    divs += "  <div style=\"" + heightWidth + "background-color: rgba(255, 0, 0, 1);\"></div>\n";
                } else if (maze.body[row][column]) {
                    divs += "  <div style=\"" + heightWidth + "background-color: rgba(255, 255, 255, 1);\"></div>\n";
                } else {
                    divs += "  <div style=\"" + heightWidth + "background-color: rgba(0, 0, 0, 1);\"></div>\n";
                }
            }
        }

        System.out.println("<div style=\"display:grid;grid-template-columns:" + columns + "\">\n" +
                divs +
                "</div>");
    }
}
