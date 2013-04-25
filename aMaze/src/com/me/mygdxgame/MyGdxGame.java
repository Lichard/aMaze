package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MyGdxGame implements ApplicationListener {
	private float w, h;
	private OrthographicCamera camera;
	private int xsize, ysize;
	private ShapeRenderer lineRenderer;
	private ShapeRenderer squareRenderer;
	private MazeMap map;
	private Algorithm algo;
	private Stage stage;
	private Table table;
	private boolean run;

	public void setSize(int x, int y) {
		xsize = x;
		ysize = y;
		map = new MazeMap(xsize, ysize);
	}

	public void setAlgorithm(int selector) {
		switch (selector) {
		case 1:
			algo = new RecursiveBacktrackerAlgorithm(map);
			break;
		case 2:
			algo = new PrimAlgorithm(map);
			break;
		case 3:
			algo = new TestAlgorithm(map);
			break;
		}
	}

	@Override
	public void create() {
		run = true;
		setSize(50, 50);
		setAlgorithm(1);// 1 is recursive, 2 is prim
		stage = new Stage();
		table = new Table();

		Gdx.input.setInputProcessor(stage);
		lineRenderer = new ShapeRenderer();
		squareRenderer = new ShapeRenderer();
		Gdx.gl10.glLineWidth(2);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		if (run)
			run = algo.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, (int) (w * 0.6), (int) h);
		camera.update();
		camera.apply(Gdx.gl10);

		lineRenderer.setProjectionMatrix(camera.combined);
		lineRenderer.begin(ShapeType.Line);
		lineRenderer.setColor(0, 0, 0, 1);
		squareRenderer.setProjectionMatrix(camera.combined);
		squareRenderer.begin(ShapeType.FilledRectangle);
		squareRenderer.setColor(1, 0, 0, 0.2f);

		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				if ((map.get(i, j) & MazeMap.RIGHT) == 0) {
					lineRenderer.line((i + 1) * w / xsize, -j * h / ysize,
							(i + 1) * w / xsize, -(j + 1) * h / ysize);
				}
				if ((map.get(i, j) & MazeMap.DOWN) == 0) {
					lineRenderer.line((i) * w / xsize, -(j + 1) * h / ysize,
							(i + 1) * w / xsize, -(j + 1) * h / ysize);
				}
			}
		}
		squareRenderer.filledRect((map.current.x) * w / xsize, -(map.current.y + 1) * h / ysize, w
				/ xsize, h / ysize);

		lineRenderer.line(0, 0, w, 0);
		lineRenderer.line(0, 0, 0, -h);
		lineRenderer.end();
		squareRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);
		camera.translate(w / 2, -h / 2);
		table.setBounds(w*0.6f, 0, w*0.4f, h);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
