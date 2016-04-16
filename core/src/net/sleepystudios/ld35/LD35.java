package net.sleepystudios.ld35;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
		
		if(showHitboxes) renderHitboxes();
		
		update();
	}
	
	private void renderHitboxes() {
		sr.setProjectionMatrix(mh.camera.combined);
		sr.begin(ShapeType.Line);
		
		sr.setColor(Color.RED);
		for(int i=0; i<mh.tileRects.size; i++) {
			sr.rect(mh.tileRects.get(i).x, mh.tileRects.get(i).y, mh.tileRects.get(i).width, mh.tileRects.get(i).height);
		}
		
		sr.setColor(Color.YELLOW);
		sr.rect(player.x, player.y, player.getWidth(), player.getHeight());
		
		sr.end();
	}
	
	private void update() {
		//float delta = Gdx.graphics.getDeltaTime()*1000f;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	private boolean showHitboxes;
	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Input.Keys.B) showHitboxes = !showHitboxes;
		
		if(keycode==Input.Keys.NUM_1) {
			player.transform(0);
		} else if(keycode==Input.Keys.NUM_2) {
			player.transform(1);
		} else if(keycode==Input.Keys.NUM_3) {
			player.transform(2);
		}
		
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
	
	// generates a random number
    public static int rand(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    public static float rand(float min, float max) {
        return min + new Random().nextFloat() * (max - min);
    }

    // random number that cannot be 0
    public static int randNoZero(int min, int max) {
        int r = rand(min, max);

        if (r != 0) {
            return r;
        } else {
            return randNoZero(min, max);
        }
    }
    public static float randNoZero(float min, float max) {
        float r = rand(min, max);

        if (r != 0) {
            return r;
        } else {
            return randNoZero(min, max);
        }
    }
}
