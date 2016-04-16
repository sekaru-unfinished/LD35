package net.sleepystudios.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	private MapHandler mh;
	private Animation[] anim = new Animation[1];
	private int animIndex = 0; // 0 = snake, 1 = frog, 2 = bear, 3 = fish, 4 = bird
	private float tmrAnim, frameTime = 0.1f;
	
	public float x = 20, y = 520;
	
	public Player(MapHandler mh) {
		this.mh = mh;
		
		anim[0] = new Animation(frameTime, AnimGenerator.gen("snake.png", 22, 5));
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(anim[animIndex].getKeyFrame(tmrAnim, true), x, y);
		
		update();
	}
	
	public void update() {
		tmrAnim+=Gdx.graphics.getDeltaTime();
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(!isBlocked(x, y+1)) y++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        	if(!isBlocked(x-1, y)) x--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        	if(!isBlocked(x, y-1)) y--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        	if(!isBlocked(x+1, y)) x++;
        }
		mh.updateCam(x, y);
	}
	
	public int getWidth() {
		return anim[animIndex].getKeyFrame(tmrAnim).getRegionWidth();
	}
	
	public int getHeight() {
		return anim[animIndex].getKeyFrame(tmrAnim).getRegionHeight();
	}
	
	private boolean isBlocked(float x, float y) {
		Rectangle me = new Rectangle(x, y, getWidth(), getHeight());
		
		for(Rectangle r : mh.tileRects) {
			if(Intersector.overlaps(me, r)) return true;
		}
		
		return false;
	}
}
