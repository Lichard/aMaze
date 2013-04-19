package com.me.mygdxgame;

import java.util.ArrayList;

public class PrimAlgorithm extends Algorithm {
	
	private static final int IN = 0;
	private static final int FRONTIER = 1;
	private static final int OUT = 2;
	
	private int[][] states;
	private ArrayList<Cell> frontierCells = new ArrayList<Cell>();
	
	private int frontierCount = 0;
	
	private int[] inNeighbors = new int[4];
	
	public PrimAlgorithm(int[][] matrix){
		startX = rand.nextInt(matrix.length-1);
		startY = rand.nextInt(matrix[0].length-1);
		maze = matrix;
		width = matrix.length;
		height = matrix[0].length;
		states = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				states[i][j] = OUT;
			}
		}
		Cell startPosition = new Cell(startX, startY);
		currentCell = startPosition;
		
		states[currentCell.xcoordinate][currentCell.ycoordinate] = IN;
		for(int i = 0; i < 4; i++){
			switch(i){
			case UP:
				if(currentCell.ycoordinate > 0){
					Cell frontierCell = new Cell(currentCell.xcoordinate, currentCell.ycoordinate-1);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case RIGHT:
				if(currentCell.xcoordinate < width -1){
					Cell frontierCell = new Cell(currentCell.xcoordinate+1, currentCell.ycoordinate);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case DOWN:
				if(currentCell.ycoordinate < height-1){
					Cell frontierCell = new Cell(currentCell.xcoordinate, currentCell.ycoordinate+1);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case LEFT:
				if(currentCell.xcoordinate > 0){
					Cell frontierCell = new Cell(currentCell.xcoordinate-1, currentCell.ycoordinate);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			}
		}
	}
	
	public PrimAlgorithm(int[][] matrix, int x, int y){
		startX = x;
		startY = y;
		maze = matrix;
		width = matrix.length;
		height = matrix[0].length;
		states = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				states[i][j] = OUT;
			}
		}
		Cell startPosition = new Cell(startX, startY);
		currentCell = startPosition;
		
		states[currentCell.xcoordinate][currentCell.ycoordinate] = IN;
		for(int i = 0; i < 4; i++){
			switch(i){
			case UP:
				if(currentCell.ycoordinate > 0){
					Cell frontierCell = new Cell(currentCell.xcoordinate, currentCell.ycoordinate-1);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case RIGHT:
				if(currentCell.xcoordinate < width -1){
					Cell frontierCell = new Cell(currentCell.xcoordinate+1, currentCell.ycoordinate);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case DOWN:
				if(currentCell.ycoordinate < height-1){
					Cell frontierCell = new Cell(currentCell.xcoordinate, currentCell.ycoordinate+1);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case LEFT:
				if(currentCell.xcoordinate > 0){
					Cell frontierCell = new Cell(currentCell.xcoordinate-1, currentCell.ycoordinate);
					states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			}
		}
	}
	
	@Override
	public boolean update() {
		if(frontierCount > 0){
			int frontierIndex = rand.nextInt(frontierCount);
			Cell frontier = frontierCells.get(frontierIndex);
			
			int inNeighborCount = 0;
			for(int i = 0; i < 4; i++){
				switch(i){
				case UP:
					if(frontier.ycoordinate > 0 && states[frontier.xcoordinate][frontier.ycoordinate-1] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				case RIGHT:
					if(frontier.xcoordinate < width-1 && states[frontier.xcoordinate+1][frontier.ycoordinate] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				case DOWN:
					if(frontier.ycoordinate < height-1 && states[frontier.xcoordinate][frontier.ycoordinate+1] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				case LEFT:
					if(frontier.xcoordinate > 0 && states[frontier.xcoordinate-1][frontier.ycoordinate] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				}
			}
			//the drawing part, didn't implement this part yet need to do this
			
			carve(inNeighbors[rand.nextInt(inNeighborCount)]);
		
			//Mark this cell as IN
			states[frontier.xcoordinate][frontier.ycoordinate] = IN;
			frontierCells.remove(frontierIndex);
			frontierCount--;
			
			//Mark any neighboring OUT cells as FRONTIER
			
			for(int i = 0; i < 4; i++){
				switch(i){
				case UP:
					if(frontier.ycoordinate > 0 && states[frontier.xcoordinate][frontier.ycoordinate-1] == OUT){
						Cell frontierCell = new Cell(frontier.xcoordinate, frontier.ycoordinate-1);
						states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				case RIGHT:
					if(frontier.xcoordinate < width-1 && states[frontier.xcoordinate+1][frontier.ycoordinate] == OUT){
						Cell frontierCell = new Cell(frontier.xcoordinate+1, frontier.ycoordinate);
						states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				case DOWN:
					if(frontier.ycoordinate < height-1 && states[frontier.xcoordinate][frontier.ycoordinate+1] == OUT){
						Cell frontierCell = new Cell(frontier.xcoordinate, frontier.ycoordinate+1);
						states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				case LEFT:
					if(frontier.xcoordinate > 0 && states[frontier.xcoordinate-1][frontier.ycoordinate] == OUT){
						Cell frontierCell = new Cell(frontier.xcoordinate-1, frontier.ycoordinate);
						states[frontierCell.xcoordinate][frontierCell.ycoordinate] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int[][] getMaze() {
		return maze;
	}

}
