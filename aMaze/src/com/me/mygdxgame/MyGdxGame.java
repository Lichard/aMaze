package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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

	private Label fpsLabel;
	private Button dButton;
	private Button primButton;
	private Button recursiveButton;
	private Button startButton;
	private Button stepButton;
	private Button clearButton;

	private BitmapFont buttonFont;
	private TextButtonStyle tStyle;
	private LabelStyle lStyle;
	private Skin skin;

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
		}
	}

	@Override
	public void create() {
		run = false;
		setSize(50, 50);
		setAlgorithm(1);// 1 is recursive, 2 is prim

		stage = new Stage();
		table = new Table();

		stage.addActor(table);
		skin = new Skin();
		Gdx.input.setInputProcessor(stage);

		table.debug();
		table.align(Align.top | Align.center);

		// -------------------------------------STYLES---------------------------------------------------
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());
		tStyle = new TextButtonStyle();
		tStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		tStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		tStyle.checked = skin.newDrawable("white", Color.BLUE);
		tStyle.font = skin.getFont("default");
		skin.add("default", tStyle);
		lStyle = new LabelStyle(new BitmapFont(), Color.BLACK);
		skin.add("default", lStyle);
		fpsLabel = new Label("fps:", skin);
		table.add(fpsLabel);
		table.row();

		//-------------------------BUTTONS--------------------------------
		dButton = new TextButton("Run", skin);
		table.add(dButton);
		table.row();
		dButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				if(dButton.isChecked()){
					((TextButton) dButton).setText("Stop");
					run = true;
				}
				else 
					((TextButton) dButton).setText("Run");
			}
		});

		primButton = new TextButton("Prim's Algorithm", skin);
		recursiveButton = new TextButton("Recursive Backtracker", skin);
		table.add(primButton);
		table.add(recursiveButton);

		lineRenderer = new ShapeRenderer();
		squareRenderer = new ShapeRenderer();
		Gdx.gl10.glLineWidth(1);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		if (run) {
			for (int i = 0; i < 1; i++) {
				run = algo.update();
			}
		}
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		camera.apply(Gdx.gl10);
		fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());

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
		squareRenderer.filledRect((map.current.x) * w / xsize,
				-(map.current.y + 1) * h / ysize, w / xsize, h / ysize);

		lineRenderer.line(0, 0, w, 0);
		lineRenderer.line(0, 0, 0, -h);
		lineRenderer.end();
		squareRenderer.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		w = Gdx.graphics.getWidth() * 0.6f;
		h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width, height);
		camera.translate(width / 2, -height / 2);
		table.setPosition(width*0.82f, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
