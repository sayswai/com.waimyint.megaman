import java.awt.Rectangle;

import com.jogamp.newt.event.KeyEvent;


public class SpriteCharacter {

	/*Position of sprite (x, y)*/
	protected int[] Pos = new int[]{10, 10};
	/*Position of sprite relative to Camera*/
	protected int[] rPos = new int[]{3, 3};
	
	/*Texture of Sprites*/
	AnimationData idleLeft, idleRight, rightMove, leftMove, idleTex;
	AnimationData curr;
	
	/*Shape*/
	protected Rectangle shape; 
	
	/*Player Statistics*/
	protected String name;
	protected int speed;
	protected boolean isAi = false;
	protected boolean isAimoving = false;
	protected boolean onScreen = true;
	protected boolean inJump = false;
	protected boolean beginJump = false;
	protected boolean noClip = false;
	protected boolean endJump = false;
	protected int	jumpVal = 0;
	protected int direction = 0; //0 = left, 1 = right
	protected int health;
	
	/*Player's Various Weapons*/
	protected Projectile[] projectiles;
	
	class AnimationData{
		SpriteAnimationDef def;
		int counter, curFrame, direction; //direction = 0  = counting up || direction = 1 = counting down
		int[] curFrameSize = new int[2];
		float secsUnitNextFrame;
		
		void update()
		{
			if(secsUnitNextFrame != 0)
			{
				float delta = System.nanoTime() - secsUnitNextFrame;
				if(delta>=def.getFrameTime(counter)){
					/*
					if(direction == 0 && counter == def.frames.length-1)//counting up and hit the right end of the frames
					{
						direction = 1; //count down
						counter--;
					}else if(direction == 1 && counter == 0){//counting down and hit the left end of the frames
						direction = 0; //count up
						counter++;
					}else if(direction == 0){//counting up
						counter++;
					}else if(direction == 1){//counting down
						counter--;
					}*/
					if(counter == def.frames.length-1)
					{
						counter = 0;
					}else{
						counter++;
					}
				curFrame = def.getFrameTex(counter);
				curFrameSize = def.size;
				secsUnitNextFrame = System.nanoTime();
				}
				
				
			}else{
				this.counter = 0; this.direction = 0;
				secsUnitNextFrame = System.nanoTime();
				curFrame = def.getFrameTex(counter);
				curFrameSize = def.size;
			}
		}
		
		void draw()
		{
			TGAController.glDrawSprite(Window.gl, curFrame, rPos[0], rPos[1], curFrameSize[0]+10, curFrameSize[1]+10);
			//TGAController.glDrawSprite(Window.gl, curFrame, Pos[0], Pos[1], curFrameSize[0]+10, curFrameSize[1]+10);
		}
	}
	
	public SpriteCharacter(String name, int speed, boolean isAi)
	{
		this.name = name;
		this.speed = speed; Camera.movingSpeed = this.speed;
		this.isAi = isAi;
		this.health = 100;
		
		idleLeft = new AnimationData();
		idleRight = new AnimationData();
		rightMove = new AnimationData();
		leftMove = new AnimationData();
		idleTex = new AnimationData();
		curr = idleLeft;
		
		idleLeft.secsUnitNextFrame = 0;
		idleRight.secsUnitNextFrame = 0;
		rightMove.secsUnitNextFrame = 0;
		leftMove.secsUnitNextFrame = 0;
		idleTex.secsUnitNextFrame = 0;
		
		shape = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	/*
	public void loadTexture(String direction, String texture)
	{
		 
		if(direction.equals("up")){
			upTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
		}else if(direction.equals("down")){
			downTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
		}else if(direction.equals("right")){
			rightTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
		}else{
			leftTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
		}
		
	}
	*/
	
	public void draw()
	{
		if(onScreen){//only draws if on screen
			if(!isAi){
				if(direction == 0 && Keyboard.getKbPrevState()[KeyEvent.VK_A])//left
				{
					leftMove.update();
					leftMove.draw();
				}else if(direction == 1 && Keyboard.getKbPrevState()[KeyEvent.VK_D]){//right
					rightMove.update();
					rightMove.draw();
				}else if(direction == 0)
				{
					idleLeft.draw();
					curr = idleLeft;
				}else{
					idleRight.draw();
					curr = idleRight;
					
				}
			}else{
				
					idleTex.draw();
				if(projectiles.length > 0)
				{
					for(int i = 0; i < projectiles.length; i++)
					{
						projectiles[i].draw();
					}
				}
			}
		}
	}
	
	public void collisionResolution(Player boo) {
	}
	
	public void addProjectile(Projectile...args) {
		if(projectiles == null)
		{
			projectiles = new Projectile[args.length];
			for(int i = 0; i < projectiles.length; i++)
			{
				projectiles[i] = args[i];
			}
		}else{
			int length = projectiles.length + args.length;
			Projectile[] recent = new Projectile[length];
			for(int i = 0; i < projectiles.length; i++)//copy old projectiles into new array
			{
				recent[i] = projectiles[i];
			}
			for(int i = projectiles.length; i < recent.length; i++)//copy projectiles passed in arg. into new array
			{
				recent[i] = args[i-projectiles.length];
			}
			projectiles = new Projectile[length];
			for(int i = 0 ; i < projectiles.length; i++)
			{
				projectiles[i] = recent[i];
			}
		}
		
	}
	
	public boolean overlap(Player player)
	{//using AABB check
		Rectangle s = player.shape;
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
	
	public int[] getPos() {
		return Pos;
	}

	public void setPos(int[] pos) {
		Pos = pos;
	}
	 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setX(int x) {
		Pos[0] = x;
	}
	public void setY(int y){
		Pos[1] = y;
	}
	public int getX()
	{
		return Pos[0];
	}
	public int getY()
	{
		return Pos[1];
	}
	public int getWidth()
	{
		return curr.curFrameSize[0];
	}
	public int getHeight()
	{
		return curr.curFrameSize[1];
	}
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		Camera.movingSpeed = this.speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	
	
}
