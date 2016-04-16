package net.sleepystudios.ld35;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	private MapHandler mh;
	
	private Animation[] anim = new Animation[3];
	public final int SNAKE = 0, FISH = 1, FROG = 2, BIRD = 10;
	public int animIndex;
	private float tmrAnim, frameTime = 0.1f;
	
	public float x = 20, y = 520;
	public int dir; // 0 = right, 1 = left
	
	public Player(MapHandler mh) {
		this.mh = mh;
		
		anim[0] = new Animation(frameTime, AnimGenerator.gen("snake.png", 22, 5));
		anim[1] = new Animation(frameTime, AnimGenerator.gen("fish.png", 12, 8));
		anim[2] = new Animation(frameTime, AnimGenerator.gen("frog.png", 24, 11));
		
		mh.updateCam(x, y);
	}
	
	public void render(SpriteBatch batch) {
		boolean flip = (dir==1);
		
		batch.draw(anim[animIndex].getKeyFrame(tmrAnim, true), flip ? x+getWidth() : x, y, flip ? -getWidth() : getWidth(), getHeight());
		
		update();
	}
	
	private int SPEED[] = new int[] {100, 20, 200};
	private float speed;
	
	private boolean jumping, falling;
	private float yVel;
	private float startVel = 2f, airResistance = 0.2f, gravityPull = 0.1f;
	public void update() {
		float delta = Gdx.graphics.getDeltaTime();
		
		// speed
		speed = SPEED[animIndex] * (delta);
		
		// update
		switch(animIndex) {
		case SNAKE:
			updateSnake(delta);
			break;
		case FISH:
			updateFish(delta);
			break;
		case FROG:
			updateFrog(delta);
			break;
		}
		
        // jumping
        if(jumping) {
        	yVel -= airResistance;
        	if(yVel<=0) {
        		jumping = false;
        		falling = true;
        		yVel = startVel/2;
        	}
        	move(x, y+yVel);
        // falling
        } else if(falling) {
        	yVel += gravityPull;
        	if(!isBlocked(x, y-yVel)) {
        		move(x, y-yVel);
        	} else {
        		falling = false;
        		yVel = 0;
        	}
        }
        
        // general gravity
        if(!falling && !jumping) {
        	if(!isBlocked(x, y-yVel)) {
        		move(x, y-yVel);
        		yVel += 0.1f;
        	} else {
        		yVel = 0.1f;
        	}
        }
	}
	
	private void updateSnake(float delta) {
		if(isMoving()) tmrAnim+=delta;
		
		// left
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        	dir = 1;
        	if(!isBlocked(x-speed, y)) move(x-speed, y);
        }
        
        // right
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	dir = 0;
        	if(!isBlocked(x+speed, y)) move(x+speed, y);
        }
        
        // normal jumping
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !jumping && !falling) {
        	jumping = true;
        	yVel = startVel;
        }
	}
	
	private void updateFish(float delta) {
		tmrAnim+=delta;
		
		// left
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        	dir = 1;
        	if(!isBlocked(x-speed, y)) move(x-speed, y);
        }
        
        // right
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	dir = 0;
        	if(!isBlocked(x+speed, y)) move(x+speed, y);
        }
        
        // fish jumping
        if(!jumping && !falling && yVel==0) {
        	jumping = true;
        	yVel = LD35.rand(1f, 3f);
        }
	}
	
	private void updateFrog(float delta) {
		//tmrAnim+=delta;
	}
	
	private void updateBear(float delta) {
		tmrAnim+=delta;
		
		// left
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        	dir = 1;
        	if(!isBlocked(x-speed, y)) move(x-speed, y);
        }
        
        // right
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	dir = 0;
        	if(!isBlocked(x+speed, y)) move(x+speed, y);
        }
        
        // normal jumping
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !jumping && !falling) {
        	jumping = true;
        	yVel = startVel;
        }
	}
	
	private void updateBird(float delta) {
		// animation
		tmrAnim+=delta;
		
		// bird y axis
		if(animIndex==BIRD) {
			if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if(!isBlocked(x, y+speed)) move(x, y+speed);
	        }
			if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	        	if(!isBlocked(x, y-speed)) move(x, y-speed);
	        }
		}
	}
	
	public void transform(int animIndex) {
		this.animIndex = animIndex;
		tmrAnim = 0;
	}
	
	private void move(float x, float y) {
		this.x = x;
		this.y = y;
		
		if(animIndex!=FISH) mh.updateCam(x, y);
	}
	
	private boolean isMoving() {
		return (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)
				|| (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) && animIndex==BIRD);
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
