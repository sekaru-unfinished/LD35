package net.sleepystudios.ld35;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapHandler {
	public TiledMap map; 
	private TiledMapRenderer mapRenderer;
	
	public MapHandler(TiledMap map) {
		this.map = map;
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public void render(OrthographicCamera camera) {
		mapRenderer.setView(camera);
        mapRenderer.render();
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
}
