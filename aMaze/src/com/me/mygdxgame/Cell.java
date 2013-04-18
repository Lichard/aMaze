package com.me.mygdxgame;

public class Cell {
	public int xcoordinate;
	public int ycoordinate;
	
	public Cell(int x, int y){
		xcoordinate = x;
		ycoordinate = y;
	}
	
	public int getXcoord(){
		return xcoordinate;
	}
	
	public int getYcoord(){
		return ycoordinate;
	}
	
	public String toString(){
		return "(" + xcoordinate + ", " + ycoordinate +")";
	}
}
