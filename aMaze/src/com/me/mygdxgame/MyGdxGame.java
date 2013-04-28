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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
	private boolean solve;
	private int algoSelection;
	private int stepsPerFrame;

	private Label fpsLabel;
	private Button primButton;
	private Button recursiveButton;
	private Button startButton;
	private Button stepButton;
	private Button clearButton;
	private Button solveButton;
	private Button fastButton;
	private Button slowButton;

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
		run = solve = false;
		setSize(50, 50);
		algoSelection = 1;
		stepsPerFrame = 1;
		setAlgorithm(algoSelection);// 1 is recursive, 2 is prim

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
		tStyle.down = skin.newDrawable("white", Color.BLUE);
		tStyle.font = skin.getFont("default");
		skin.add("default", tStyle);
		lStyle = new LabelStyle(new BitmapFont(), Color.BLACK);
		skin.add("default", lStyle);
		fpsLabel = new Label("fps:", skin);
		table.add(fpsLabel);
		table.row();

		// -------------------------BUTTONS--------------------------------
		startButton = new TextButton("Run", skin);
		table.add(startButton);
		startButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				if (startButton.isChecked()) {
					((TextButton) startButton).setText("Stop");
					run = true;
				} else {
					((TextButton) startButton).setText("Run");
					run = false;
				}
			}
		});

		stepButton = new TextButton("Step", skin);
		table.add(stepButton);
		stepButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				for (int i = 0; i < stepsPerFrame; i++) {
					algo.update();
				}
				return false;
			}
		});
		table.row();

		clearButton = new TextButton("Clear", skin);
		table.add(clearButton);
		clearButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				map = new MazeMap(xsize, ysize);
				setAlgorithm(algoSelection);
				return false;
			}
		});
		table.row();

		primButton = new TextButton("Prim's Algorithm", skin);
		recursiveButton = new TextButton("Recursive Backtracker", skin);
		table.add(primButton);
		table.add(recursiveButton);
		primButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				algoSelection = 2;
				setSize(xsize, ysize);
				setAlgorithm(algoSelection);
				return false;
			}
		});
		recursiveButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				algoSelection = 1;
				setSize(xsize, ysize);
				setAlgorithm(algoSelection);
				return false;
			}
		});
		table.row();
		solveButton = new TextButton("Solve", skin);
		table.add(solveButton);
		solveButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				solve = true;
				return false;
			}
		});
		table.row();
		fastButton = new TextButton("+speed", skin);
		table.add(fastButton);
		fastButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				stepsPerFrame++;
				return false;
			}
		});
		slowButton = new TextButton("-speed", skin);
		table.add(slowButton);
		slowButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (stepsPerFrame > 0)
					stepsPerFrame--;
				return false;
			}
		});

		lineRenderer = new ShapeRenderer();
		squareRenderer = new ShapeRenderer();
		Gdx.gl10.glLineWidth(1);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render() {
		for (int i = 0; i < stepsPerFrame; i++) {
			if (run) {
				run = algo.update();
			} else if (solve) {
				solve = algo.solve();
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
		squareRenderer.setColor(0, 1, 0, 0.2f);

		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				if ((map.get(i, j) & MazeMap.RIGHT) == 0) {
					lineRenderer.line((i + 1) * w / xsize, -j * h / ysize,
							(i + 1) * w / xsize, -(j + 1) * h / ysize);
				}
				if ((map.get(i, j) & MazeMap.DOWN) == 0) {
					if (!(i == xsize - 1 && j == xsize - 1)) {
						lineRenderer.line((i) * w / xsize,
								-(j + 1) * h / ysize, (i + 1) * w / xsize,
								-(j + 1) * h / ysize);
					}
				}
				if ((map.get(i, j) & MazeMap.GREEN) == 0) {
					if (!(i == xsize - 1 && j == xsize - 1)) {
						squareRenderer.filledRect((map.current.x) * w / xsize,
								-(map.current.y + 1) * h / ysize, w / xsize, h
										/ ysize);
					}
				}
			}
		}
		squareRenderer.setColor(1, 0, 0, 0.2f);
		squareRenderer.filledRect((map.current.x) * w / xsize,
				-(map.current.y + 1) * h / ysize, w / xsize, h / ysize);

		lineRenderer.line(w / xsize, 0, w, 0);
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
		camera = new OrthographicCamera(width + 10, height + 10);
		camera.translate(width / 2, -height / 2);
		table.setPosition(width * 0.82f, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
