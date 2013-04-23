package com.me.mygdxgame;

import java.util.Random;

public abstract class Algorithm {
	
	protected int startX;
	protected int startY;
	protected int width;
	protected int height;
	protected Random rand = new Random();
	
	protected MazeMap map;
	
	protected Cell currentCell;
	
	public Algorithm(MazeMap map){
		startX = startY = 0;
		this.map = map;
		width = map.xsize;
		height = map.ysize;
	}
	
	public abstract boolean update();
	
	public void carve(int direction){
		
	}

	public MazeMap getMaze() {
		return map;
	}
	

}