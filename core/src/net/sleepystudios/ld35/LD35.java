package net.sleepystudios.ld35;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LD35 extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
    private MapHandler mapHandler;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mapHandler = new MapHandler(new TmxMapLoader().load("map.tmx"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
        mapHandler.render(camera);
		
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		

		batch.end();
		
		update(Gdx.graphics.getDeltaTime()*1000f);
	}
	
	public void updateCam(float x, float y) {
    	int w = mapHandler.getWidth();
    	int h = mapHandler.getHeight();
    	
    	float minX = camera.zoom * (camera.viewportWidth / 2);
        float maxX = w - minX;
        float minY = camera.zoom * (camera.viewportHeight / 2);
        float maxY = h - minY;
        
        camera.position.set(Math.min(maxX, Math.max(x, minX)), Math.min(maxY, Math.max(y, minY)), 0);
    }
	
	private void update(float delta) {
		
	}
}
