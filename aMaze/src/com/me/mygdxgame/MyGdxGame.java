package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MyGdxGame implements ApplicationListener {
	private float w, h;
	private OrthographicCamera camera;
	private OrthographicCamera cameraUI;
	private SpriteBatch batch;
	private int xsize, ysize;
	private ShapeRenderer lineRenderer;
	private ShapeRenderer squareRenderer;
	private MazeMap map;
	private Algorithm algo;

	public void setSize(int x, int y){
		
	}
	
	public void setAlgorithm(int selector){
		
	}
	
	@Override
	public void create() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		xsize = 10;
		ysize = 10;
		map = new MazeMap(xsize,ysize);
		algo = new RecursiveBacktrackerAlgorithm(map);
		
		camera = new OrthographicCamera(w, h);
		cameraUI = new OrthographicCamera(220, 480);
		camera.translate(w / 2, h / 2);
		batch = new SpriteBatch();
		
		lineRenderer = new ShapeRenderer();
		squareRenderer = new ShapeRenderer();
		Gdx.gl10.glLineWidth(3);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		algo.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, 480, 480);
		camera.update();
		camera.apply(Gdx.gl10);
		

		lineRenderer.setProjectionMatrix(camera.combined);
		lineRenderer.begin(ShapeType.Line);
		lineRenderer.setColor(0, 0, 0, 1);
		squareRenderer.setProjectionMatrix(camera.combined);
		squareRenderer.begin(ShapeType.FilledRectangle);
		squareRenderer.setColor(1, 0, 0, 0.5f);

		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				if ((map.get(i, j) & MazeMap.RIGHT) == 0) {// if not marked as tunneled
					lineRenderer.line((i + 1) * w / xsize, j * h / ysize,
							(i + 1) * w / xsize, (j + 1) * h / ysize);
				}
				if ((map.get(i, j) & MazeMap.UP) == 0) {// if not marked as tunneled
					lineRenderer.line((i) * w / xsize, (j + 1) * h / ysize,
							(i + 1) * w / xsize, (j + 1) * h / ysize);
				}
				if ((map.get(i, j) & MazeMap.CURSOR) == 1) {// if marked as selected
					squareRenderer.filledRect((i) * w / xsize, j * h / ysize, w
							/ xsize, h / ysize);
				}
			}
		}
		lineRenderer.line(0, 0, w, 0);
		lineRenderer.line(0, 0, 0, h);
		lineRenderer.end();
		
		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glViewport(480, 0, 220, 480);
		cameraUI.update();
		cameraUI.apply(Gdx.gl10);
		squareRenderer.setProjectionMatrix(cameraUI.combined);
		squareRenderer.filledRect(0, 0, 50, 50);
		squareRenderer.end();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
