package com.me.mygdxgame;

import java.util.ArrayList;

public class MazeMap {
	public int xsize;
	public int ysize;
	public Cell current; 
	private ArrayList<ArrayList<Integer>> map;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 4;
	public static final int LEFT = 8;
	public static final int CURSOR = 16;
	public static final int IN = 32;
	public static final int FRONTIER = 64;
	public static final int OUT = 128; 
	
	public MazeMap(int x, int y){
		xsize=x;
		ysize=y;
		current = new Cell(0,0);
		map = new ArrayList<ArrayList<Integer>>();
		for (int i =0; i<ysize;i++){
			map.add(new ArrayList<Integer>());
			for(int j=0;j<y;j++){
				map.get(i).add(new Integer(0));
			}
		}
	}
	
	public int get(int x, int y){
		return map.get(x).get(y);
	}
	
	public void set(int x, int y, int a){
		int dir = map.get(x).get(y);
		if((dir & a)!=1){
			map.get(x).set(y, dir+a);
		}
		//TODO REMOVE ADJACENT
	}
}
