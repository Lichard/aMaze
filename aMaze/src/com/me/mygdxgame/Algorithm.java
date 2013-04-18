package com.me.mygdxgame;

import java.util.Random;

public abstract class Algorithm {
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public int startX;
	public int startY;
	
	public int[][] maze;
	
	public int height;
	public int width;
	
	public Cell currentCell;
	
	public Random rand = new Random();
	
	public abstract boolean update();
	
	public abstract int[][] getMaze();
	
	public void carve(int direction){
		
	}
}