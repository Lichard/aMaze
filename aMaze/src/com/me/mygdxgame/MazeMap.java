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
		int has;
		int dir = map.get(x).get(y);
		has = dir & a;
		return has != 0;
	}

	public void remove(int x, int y, int a) {
		int dir = map.get(x).get(y);
		if ((dir & a) != 0) { // Set main Cell
			map.get(x).set(y, dir - a);
		}
	}

	public void set(int x, int y, int a) {
		int dir = map.get(x).get(y);
		if ((dir & a) == 0) { // Set main Cell
			map.get(x).set(y, dir + a);
		}
		// --------Set corresponding adjacent cell
		if (a == UP) {
			dir = map.get(x).get(y - 1);
			if ((dir & DOWN) == 0) {
				map.get(x).set(y - 1, dir + DOWN);
			}
		}
		if (a == DOWN) {
			dir = map.get(x).get(y + 1);
			if ((dir & UP) == 0) {
				map.get(x).set(y + 1, dir + UP);
			}
		}
		if (a == LEFT) {
			dir = map.get(x - 1).get(y);
			if ((dir & RIGHT) == 0) {
				map.get(x - 1).set(y, dir + RIGHT);
			}
		}
		if (a == RIGHT) {
			dir = map.get(x + 1).get(y);
			if ((dir & LEFT) == 0) {
				map.get(x + 1).set(y, dir + LEFT);
			}
		}
	}
}
