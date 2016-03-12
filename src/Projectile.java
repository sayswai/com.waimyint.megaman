import java.awt.Rectangle;




public class Projectile {

	int startingX;
	int startingY;
	int endX;
	int endY;
	int x;
	int y;
	int velocity;
	int counter;
	boolean inMotion = false;
	boolean reached = false;
	boolean targetHit = false;
	SpriteAnimationDef motion;
	float secsSinceLastFrame;
	float lastMoveTime;
	float deltaMoveTime;
	int currTex;
	int[] texSize = new int[2];
	Rectangle shape;
	
	public Projectile(String name, String[] tex, int velocity)
	{
		this.secsSinceLastFrame = 0;
		this.velocity = velocity;
		
		motion = new SpriteAnimationDef(name, tex.length, tex);
		counter = 0;
		currTex = motion.getFrameTex(counter);
		texSize = motion.size;
		shape = new Rectangle(x, y, texSize[0], texSize[1]);
	}
	
	public void updateFrame()
	{
		if(secsSinceLastFrame != 0)
		{
			float delta = System.nanoTime() - secsSinceLastFrame;
			if(delta >= motion.getFrameTime(counter)){
				if(counter == motion.frames.length-1)
				{
					counter = 0;
				}else{
					counter++;
				}
				currTex = motion.getFrameTex(counter);
				texSize = motion.size;
				shape.resize(texSize[0], texSize[1]);
				secsSinceLastFrame = System.nanoTime();
			}
		}else{
			secsSinceLastFrame = System.nanoTime();
		}
	}
	
	
	public void draw()
	{
		if(x == 0 || y == 0){
			x = startingX;
			y = startingY;
		}

		int xx = x - Camera.x;
		int yy = y - Camera.y;
		TGAController.glDrawSprite(Window.gl, currTex, xx, yy, texSize[0], texSize[1]);
		
	}
	
	public void setFrameTime(float time)
	{
		motion.setFrameTime(time);
	}
	
	public void move() {
		if(reached)
		{
			restart();
		}
		if((System.nanoTime() - lastMoveTime) > deltaMoveTime){
			if(inMotion)
			{
				if(x > endX)
				{
					x -= velocity;
				}
				if(x < endX)
				{
					x += velocity;
				}
				if(y > endY)
				{
					y -= velocity;
				}
				if(y < endY)
				{
					y += velocity;
				}
			}
			shape.move(x, y);
			lastMoveTime = System.nanoTime();
		}
		
		if(x <= 0 || x >= Window.window.getWidth()*2)
		{
			reached = true;
		}
		
		if(x >= (endX-velocity) && x <= (endX+velocity)){
			if(y >= (endY-velocity) && y <= (endY+velocity)){
				if(x <= startingX)
				{
					endX = 0;
				}else if(x > startingX)
				{
					endX = Window.window.getWidth()*2;
				}
			}
		}
		
	}
	
	public boolean overlap(Rectangle s)
	{//using AABB check
		boolean retrn = true;
		//is box1 left of box 2
		if((shape.x+shape.width) < s.x)
		{
			retrn = false;
		}
		//is box1 right of box 2
		if(shape.x > (s.x+s.width))
		{
			retrn = false;
		}
		//is box1 above box2
		if((shape.y+shape.height) < s.y)
		{
			retrn = false;
		}
		//is box1 below box2
		if(shape.y > (s.y+s.height))
		{
			retrn = false;
		}
		return retrn;
	}
	public void restart() {
		x = startingX;
		y = startingY;
		inMotion = false;
		reached = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCurrTex() {
		return currTex;
	}

	public void setCurrTex(int currTex) {
		this.currTex = currTex;
	}

	public int getStartingX() {
		return startingX;
	}

	public void setStartingX(int startingX) {
		this.startingX = startingX;
	}

	public int getStartingY() {
		return startingY;
	}

	public void setStartingY(int startingY) {
		this.startingY = startingY;
	}

	public float getLastMoveTime() {
		return lastMoveTime;
	}

	public void setLastMoveTime(float lastMoveTime) {
		this.lastMoveTime = lastMoveTime;
	}

	public float getDeltaMoveTime() {
		return deltaMoveTime;
	}

	public void setDeltaMoveTime(float deltaMoveTime) {
		this.deltaMoveTime = deltaMoveTime;
	}

	
	
	
}
