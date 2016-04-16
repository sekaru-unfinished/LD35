package net.sleepystudios.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LD35 extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
    private MapHandler mh;
    private Player player;
    private ShapeRenderer sr;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
		mh = new MapHandler(new TmxMapLoader().load("map.tmx"));
		
		player = new Player(mh);
		sr = new ShapeRenderer();
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        mh.render();
        
        batch.setProjectionMatrix(mh.camera.combined);
		batch.begin();
		
		player.render(batch);

		batch.end();
		
		sr.setProjectionMatrix(mh.camera.combined);
		sr.begin(ShapeType.Line);
		
		sr.setColor(Color.RED);
		for(int i=0; i<mh.tileRects.size; i++) {
			sr.rect(mh.tileRects.get(i).x, mh.tileRects.get(i).y, mh.tileRects.get(i).width, mh.tileRects.get(i).height);
		}
		
		sr.setColor(Color.YELLOW);
		sr.rect(player.x, player.y, player.getWidth(), player.getHeight());
		
		sr.end();
		update();
	}
	
	private void update() {
		float delta = Gdx.graphics.getDeltaTime()*1000f;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		if(amount>0) {
			mh.camera.zoom+=0.1f;
			mh.updateCam(player.x, player.y);
		} else {
			mh.camera.zoom-=0.1f;
			mh.updateCam(player.x, player.y);
		}
		return false;
	}
	
}
