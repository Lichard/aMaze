package com.me.mygdxgame;

import java.util.Random;
import java.util.Stack;

public abstract class Algorithm {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	protected int startX;
	protected int startY;
	protected int width;
	protected int height;
	protected int endX;
	protected int endY;
	protected Random rand = new Random();

	protected MazeMap map;

	protected Cell currentCell;
	protected Cell solveCell;

	protected Stack<Cell> solveStack = new Stack<Cell>();
	protected boolean[][] solverVisit;

	public Algorithm(MazeMap map) {
		startX = startY = 0;
		width = map.xsize;
		height = map.ysize;
		this.map = map;
		endX = width - 1;
		endY = height - 1;
		solverVisit = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				solverVisit[i][j] = false;
			}
		}
		solverVisit[startX][startY] = true;

		solveCell = new Cell(startX, startY);
		Cell initial = new Cell(startX, startY);
		solveStack.push(initial);
	}

	public abstract boolean update();

	public boolean solve() {
		if (!(solveCell.x == endX && solveCell.y == endY)) {
			solverVisit[solveCell.x][solveCell.y] = true;

			int[] paths = new int[4];
			int pathCount = 0;

			for (int i = 0; i < 4; i++) {
				switch (i) {
				case UP:
					if (map.has(solveCell.x, solveCell.y, MazeMap.UP)
							&& !solverVisit[solveCell.x][solveCell.y - 1]) {
						paths[pathCount++] = i;
					}
					break;
				case RIGHT:
					if (map.has(solveCell.x, solveCell.y, MazeMap.RIGHT)
							&& !solverVisit[solveCell.x + 1][solveCell.y]) {
						paths[pathCount++] = i;
					}
					break;
				case DOWN:
					if (map.has(solveCell.x, solveCell.y, MazeMap.DOWN)
							&& !solverVisit[solveCell.x][solveCell.y + 1]) {
						paths[pathCount++] = i;
					}
					break;
				case LEFT:
					if (map.has(solveCell.x, solveCell.y, MazeMap.LEFT)
							&& !solverVisit[solveCell.x - 1][solveCell.y]) {
						paths[pathCount++] = i;
					}
					break;
				}
			}

			if (pathCount > 0) {
				solveStack.push(new Cell(solveCell.x, solveCell.y));
				switch (paths[rand.nextInt(pathCount)]) {
				case UP:
					solveCell.y--;
					break;
				case RIGHT:
					solveCell.x++;
					break;
				case DOWN:
					solveCell.y++;
					break;
				case LEFT:
					solveCell.x--;
					break;
				}
				map.set(solveCell.x, solveCell.y, MazeMap.GREEN);
			} else {
				if (solveStack.isEmpty()) {
					return false;
				} else {
					map.remove(solveCell.x, solveCell.y, MazeMap.GREEN);
					solveCell = solveStack.pop();
				}
			}
			return true;
		} else {
			map.set(solveCell.x, solveCell.y, MazeMap.GREEN);
			return false;
		}
	}

	public MazeMap getMaze() {
		return map;
	}

}