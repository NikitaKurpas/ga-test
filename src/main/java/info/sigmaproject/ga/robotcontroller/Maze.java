package info.sigmaproject.ga.robotcontroller;

import java.util.ArrayList;

public class Maze {
    private final int[][] maze;
    private XYPair startPosition = new XYPair(-1, -1);

    public Maze(int[][] maze) {
        this.maze = maze;
    }

    public XYPair getStartPosition() {
        if (this.startPosition.getX() != -1 && this.startPosition.getY() != -1) {
            return this.startPosition;
        }

        XYPair startPosition = new XYPair(0, 0);
        for (int rowIndex = 0; rowIndex < maze.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < this.maze[rowIndex].length; columnIndex++) {
                if (maze[rowIndex][columnIndex] == 2) {
                    this.startPosition = new XYPair(columnIndex, rowIndex);
                    return this.startPosition;
                }
            }
        }
        return startPosition;
    }

    public int getPositionValue(int x, int y) {
        if (x < 0 || y < 0 || x >= maze.length || y >= maze[0].length) {
            return 1;
        }
        return maze[y][x];
    }

    public boolean isWall(int x, int y) {
        return (getPositionValue(x, y) == 1);
    }

    public int getMaxX() {
        return maze[0].length - 1;
    }

    public int getMaxY() {
        return maze.length - 1;
    }

    public int scoreRoute(ArrayList<XYPair> route) {
        int score = 0;
        boolean visited[][] = new boolean[this.getMaxY() + 1][this.getMaxX() + 1];
        for (XYPair step : route) {
            if (this.maze[step.getY()][step.getX()] == 3 && !visited[step.getY()][step.getX()]) {
                score++;
                visited[step.getY()][step.getX()] = true;
            }
        }
        return score;
    }

}
