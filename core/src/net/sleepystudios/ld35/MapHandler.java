package net.sleepystudios.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MapHandler {
	public TiledMap map; 
	private TiledMapRenderer mapRenderer;
	public OrthographicCamera camera;
	public Array<Rectangle> tileRects = new Array<Rectangle>();

	public MapHandler(TiledMap map) {
		this.map = map;
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 0.25f;
		
		getCollision();
	}
	
	public void render() {
		camera.update();
		mapRenderer.setView(camera);
        mapRenderer.render();
	}
	
	public void updateCam(float x, float y) {
    	int w = getWidth();
    	int h = getHeight();
    	
    	float minX = camera.zoom * (camera.viewportWidth / 2);
        float maxX = w - minX;
        float minY = camera.zoom * (camera.viewportHeight / 2);
        float maxY = h - minY;
        
        camera.position.set(Math.min(maxX, Math.max(x, minX)), Math.min(maxY, Math.max(y, minY)), 0);
    }
	
	public int getTileSize() {
		return map.getProperties().get("tilewidth", Integer.class);
	}
	
	public int getWidth() {
		return map.getProperties().get("width", Integer.class) * getTileSize();
	}
	
	public int getHeight() {
		return map.getProperties().get("height", Integer.class) * getTileSize();
	}
	
	private void getCollision() {
		MapObjects objects = ((MapLayer) map.getLayers().get("Collision")).getObjects();
		
		for(int i=0; i<objects.getCount(); i++) {
			RectangleMapObject r = (RectangleMapObject) objects.get(i);
		    Rectangle rect = r.getRectangle();
		    
		    tileRects.add(rect);
		}
	}
}
