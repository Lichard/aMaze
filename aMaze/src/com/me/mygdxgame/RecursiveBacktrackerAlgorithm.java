package com.me.mygdxgame;

import java.util.Stack;

public class RecursiveBacktrackerAlgorithm extends Algorithm {
	
	//The fields that contain the starting position of the maze generation
	//private int startX;
	//private int startY;
	//2d array that represents the maze to be generated
	//private int[][] maze;
	//2d array that represents which cells have been visited in the maze
	private boolean[][] visited;
	//dimensions of the matrix
	//private int height;
	//private int width;
	//the stack keeps track of how deep we're going
	private Stack<Cell> stack = new Stack<Cell>();
	//the current position of where we are in the maze
	//private Cell currentCell;

	//private Random rand = new Random();

	//constructor with random start position
	public RecursiveBacktrackerAlgorithm(int[][] matrix){
		startX = rand.nextInt(matrix.length-1);
		startY = rand.nextInt(matrix[0].length-1);
		maze = matrix;
		width = matrix.length;
		height = matrix[0].length;
		visited = new boolean[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				visited[i][j] = false;
			}
		}
		Cell startPosition = new Cell(startX, startY);
		currentCell = startPosition;
		stack.push(startPosition);
	}
	//constructor with specific start position
	public RecursiveBacktrackerAlgorithm(int[][] matrix, int x, int y){
		startX = x;
		startY = y;
		maze = matrix;
		width = matrix.length;
		height = matrix[0].length;
		visited = new boolean[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				visited[i][j] = false;
			}
		}
		Cell startPosition = new Cell(startX, startY);
		currentCell = startPosition;
		stack.push(startPosition);
	}

	//continually call this method in the actual game to do a step by step of the algorithm
	@Override
	public boolean update() {
		// TODO Auto-generated method stub

		//keeps track of which neighbors are available from the current position
		if(!stack.empty()){
			int[] neighbors = new int[4];
			int freeNeighborCount = 0;
			//check whether there is a free neighbor
			for (int i = 0; i < 4; i++){
				switch(i){
				case UP:
					if(currentCell.ycoordinate > 0 && !visited[currentCell.xcoordinate][currentCell.ycoordinate-1])
						neighbors[freeNeighborCount++] = i;
					break;
				case RIGHT:
					if(currentCell.xcoordinate < width-1 && !visited[currentCell.xcoordinate + 1][currentCell.ycoordinate])
						neighbors[freeNeighborCount++] = i;
					break;
				case DOWN:
					if(currentCell.ycoordinate < height-1 && !visited[currentCell.xcoordinate][currentCell.ycoordinate+1])
						neighbors[freeNeighborCount++] = i;
					break;
				case LEFT:
					if(currentCell.xcoordinate > 0 && !visited[currentCell.xcoordinate-1][currentCell.ycoordinate])
						neighbors[freeNeighborCount++] = i;
					break;
				}
			}
			//randomly select a free neighbor to move towards
			if(freeNeighborCount > 0){

				switch(neighbors[rand.nextInt(freeNeighborCount)]){
				case UP:
					//add draw up part here
					currentCell.ycoordinate--;
					stack.push(currentCell);
					break;
				case RIGHT:
					//add draw right part here
					currentCell.xcoordinate++;
					stack.push(currentCell);
					break;
				case DOWN:
					//add draw down part here
					currentCell.ycoordinate++;
					stack.push(currentCell);
					break;
				case LEFT:
					//add draw left part here
					currentCell.xcoordinate--;
					stack.push(currentCell);
					break;
				}

			}
			else{
				currentCell = stack.pop();
			}

			return true;
		}
		else{
			return false;
		}

	}
	@Override
	public int[][] getMaze() {
		// TODO Auto-generated method stub
		return maze;
	}

}
