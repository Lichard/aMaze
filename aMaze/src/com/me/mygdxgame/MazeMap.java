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
	public static final int GREEN = 16;
	public static final int IN = 32;// YELLOW
	public static final int FRONTIER = 64;// ORANGE
	public static final int OUT = 128;// GREY

	MazeMap(int x, int y) {
		xsize = x;
		ysize = y;
		current = new Cell(-1, -1);
		map = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < ysize; i++) {
			map.add(new ArrayList<Integer>());
			for (int j = 0; j < y; j++) {
				map.get(i).add(new Integer(0));
			}
		}
	}

	public int get(int x, int y) {
		return map.get(x).get(y);
	}

	public boolean has(int x, int y, int a) {
		return ((this.get(x, y) & a) == 1);
	}

	public void remove(int x, int y, int a) {
		int dir = map.get(x).get(y);
		if ((dir & a) == 1) { // Set main Cell
			map.get(x).set(y, dir - a);
		}
	}

	public void set(int x, int y, int a) {
		int dir = map.get(x).get(y);
		if ((dir & a) != 1) { // Set main Cell
			map.get(x).set(y, dir + a);
		}
		// --------Set corresponding adjacent cell
		if (a == UP) {
			dir = map.get(x).get(y - 1);
			if ((dir & DOWN) != 1) {
				map.get(x).set(y - 1, dir + DOWN);
			}
		}
		if (a == DOWN) {
			dir = map.get(x).get(y + 1);
			if ((dir & UP) != 1) {
				map.get(x).set(y + 1, dir + UP);
			}
		}
		if (a == LEFT) {
			dir = map.get(x - 1).get(y);
			if ((dir & RIGHT) != 1) {
				map.get(x - 1).set(y, dir + RIGHT);
			}
		}
		if (a == RIGHT) {
			dir = map.get(x + 1).get(y);
			if ((dir & LEFT) != 1) {
				map.get(x + 1).set(y, dir + LEFT);
			}
		}
	}
}
