package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class PrimAlgorithm extends Algorithm {
	
	private static final int IN = 0;
	private static final int FRONTIER = 1;
	private static final int OUT = 2;
	
	private int[][] states;
	private ArrayList<Cell> frontierCells = new ArrayList<Cell>();
	
	private int frontierCount = 0;
	
	private int[] inNeighbors = new int[4];
	
	public PrimAlgorithm(MazeMap map){
		super(map);
		
		width = map.xsize;
		height = map.ysize;
		states = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				states[i][j] = OUT;
			}
		}
		Cell startPosition = new Cell(startX, startY);
		currentCell = startPosition;
		
		states[currentCell.x ][currentCell.y ] = IN;
		for(int i = 0; i < 4; i++){
			switch(i){
			case MazeMap.UP:
				if(currentCell.y  > 0){
					Cell frontierCell = new Cell(currentCell.x , currentCell.y -1);
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case MazeMap.RIGHT:
				if(currentCell.x  < width -1){
					Cell frontierCell = new Cell(currentCell.x +1, currentCell.y );
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case MazeMap.DOWN:
				if(currentCell.y  < height-1){
					Cell frontierCell = new Cell(currentCell.x , currentCell.y +1);
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case MazeMap.LEFT:
				if(currentCell.x  > 0){
					Cell frontierCell = new Cell(currentCell.x -1, currentCell.y );
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			}
		}
	}
	
	public PrimAlgorithm(MazeMap map, int x, int y){
		super(map);
		startX = x;
		startY = y;
		states = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				states[i][j] = OUT;
			}
		}
		Cell startPosition = new Cell(startX, startY);
		currentCell = startPosition;
		
		states[currentCell.x ][currentCell.y ] = IN;
		for(int i = 0; i < 4; i++){
			switch(i){
			case MazeMap.UP:
				if(currentCell.y  > 0){
					Cell frontierCell = new Cell(currentCell.x , currentCell.y -1);
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case MazeMap.RIGHT:
				if(currentCell.x  < width -1){
					Cell frontierCell = new Cell(currentCell.x +1, currentCell.y );
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case MazeMap.DOWN:
				if(currentCell.y  < height-1){
					Cell frontierCell = new Cell(currentCell.x , currentCell.y +1);
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
					frontierCells.add(frontierCell);
					frontierCount++;
				}
				break;
			case MazeMap.LEFT:
				if(currentCell.x  > 0){
					Cell frontierCell = new Cell(currentCell.x -1, currentCell.y );
					states[frontierCell.x ][frontierCell.y ] = FRONTIER;
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
				case MazeMap.UP:
					if(frontier.y  > 0 && states[frontier.x ][frontier.y -1] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				case MazeMap.RIGHT:
					if(frontier.x  < width-1 && states[frontier.x +1][frontier.y ] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				case MazeMap.DOWN:
					if(frontier.y  < height-1 && states[frontier.x ][frontier.y +1] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				case MazeMap.LEFT:
					if(frontier.x  > 0 && states[frontier.x -1][frontier.y ] == IN){
						inNeighbors[inNeighborCount++] = i;
					}
					break;
				}
			}
			//the drawing part, didn't implement this part yet need to do this
			
			carve(inNeighbors[rand.nextInt(inNeighborCount)]);
		
			//Mark this cell as IN
			states[frontier.x ][frontier.y ] = IN;
			frontierCells.remove(frontierIndex);
			frontierCount--;
			
			//Mark any neighboring OUT cells as FRONTIER
			
			for(int i = 0; i < 4; i++){
				switch(i){
				case MazeMap.UP:
					if(frontier.y  > 0 && states[frontier.x ][frontier.y -1] == OUT){
						Cell frontierCell = new Cell(frontier.x , frontier.y -1);
						states[frontierCell.x ][frontierCell.y ] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				case MazeMap.RIGHT:
					if(frontier.x  < width-1 && states[frontier.x +1][frontier.y ] == OUT){
						Cell frontierCell = new Cell(frontier.x +1, frontier.y );
						states[frontierCell.x ][frontierCell.y ] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				case MazeMap.DOWN:
					if(frontier.y  < height-1 && states[frontier.x ][frontier.y +1] == OUT){
						Cell frontierCell = new Cell(frontier.x , frontier.y +1);
						states[frontierCell.x ][frontierCell.y ] = FRONTIER;
						frontierCells.add(frontierCell);
						frontierCount++;
					}
					break;
				case MazeMap.LEFT:
					if(frontier.x  > 0 && states[frontier.x -1][frontier.y ] == OUT){
						Cell frontierCell = new Cell(frontier.x -1, frontier.y );
						states[frontierCell.x ][frontierCell.y ] = FRONTIER;
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

}
