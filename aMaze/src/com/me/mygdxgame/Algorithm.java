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
	
	public Algorithm(MazeMap map){
		startX = startY = 0;
		width = map.xsize;
		height = map.ysize;
		endX = width-1;
		endY = height-1;
		solverVisit = new boolean[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				solverVisit[i][j] = false;
			}
		}
		solverVisit[startX][startY] = true;
		
		solveCell = new Cell(startX, startY);
		Cell initial = new Cell(startX, startY);
		solveStack.push(initial);
	}
	
	public abstract boolean update();
	
	public boolean solve(){
		if(solveCell.x != endX && solveCell.y != endY){
			int[] paths = new int[4];
			int pathCount = 0;
			
			for(int i = 0; i < 4; i++){
				switch (i){
				case UP:
					if(!map.has(solveCell.x, solveCell.y, UP) &&
							!solverVisit[solveCell.x][solveCell.y-1]){
						paths[pathCount++] = i;
					}
					break;
				case RIGHT:
					if(!map.has(solveCell.x, solveCell.y, RIGHT) &&
							!solverVisit[solveCell.x+1][solveCell.y]){
						paths[pathCount++] = i;
					}
					break;
				case DOWN:
					if(!map.has(solveCell.x, solveCell.y, DOWN) &&
							!solverVisit[solveCell.x][solveCell.y+1]){
						paths[pathCount++] = i;
					}
					break;
				case LEFT:
					if(!map.has(solveCell.x, solveCell.y, LEFT) &&
							!solverVisit[solveCell.x-1][solveCell.y]){
						paths[pathCount++] = i;
					}
					break;
				}
			}
			
			if(pathCount > 0){
				switch(paths[rand.nextInt(pathCount)]){
				case UP:
					map.set(solveCell.x, solveCell.y, MazeMap.GREEN);
					solveCell.y--;
					Cell nextCellUp = new Cell(solveCell.x, solveCell.y);
					solveStack.push(nextCellUp);
					solverVisit[solveCell.x][solveCell.y] = true;
					break;
				case RIGHT:
					map.set(solveCell.x, solveCell.y, MazeMap.GREEN);
					solveCell.x++;
					Cell nextCellRight = new Cell(solveCell.x, solveCell.y);
					solveStack.push(nextCellRight);
					solverVisit[solveCell.x][solveCell.y] = true;
					break;
				case DOWN:
					map.set(solveCell.x, solveCell.y, MazeMap.GREEN);
					solveCell.y++;
					Cell nextCellDown = new Cell(solveCell.x, solveCell.y);
					solveStack.push(nextCellDown);
					solverVisit[solveCell.x][solveCell.y] = true;
					break;
				case LEFT:
					map.set(solveCell.x, solveCell.y, MazeMap.GREEN);
					solveCell.x--;
					Cell nextCellLeft = new Cell(solveCell.x, solveCell.y);
					solveStack.push(nextCellLeft);
					solverVisit[solveCell.x][solveCell.y] = true;
					break;
				}
			}
			else{
				map.remove(solveCell.x, solveCell.y, MazeMap.GREEN);
				solveCell = solveStack.pop();
			}
			return true;
		}
		else{
			return false;
		}
	}

	public MazeMap getMaze() {
		return map;
	}
	

}