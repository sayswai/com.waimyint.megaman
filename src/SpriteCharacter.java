import com.jogamp.newt.event.KeyEvent;


public class SpriteCharacter {

	/*Position of sprite (x, y)*/
	protected int[] Pos = new int[]{10, 10};
	/*Position of sprite relative to Camera*/
	protected int[] rPos = new int[]{3, 3};
	
	/*Texture of Sprites*/
	AnimationData idleLeft, idleRight, rightMove, leftMove;
	
	/*Player Statistics*/
	protected String name;
	protected int speed;
	protected boolean isAi = false;
	protected boolean isAimoving = false;
	protected boolean inJump = false;
	protected boolean beginJump = false;
	protected boolean noClip = false;
	protected boolean endJump = false;
	protected int	jumpVal = 0;
	protected int direction = 0; //0 = left, 1 = right
	
	
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
		this.speed = speed; CameraTest.movingSpeed = this.speed;
		this.isAi = isAi;
		
		idleLeft = new AnimationData();
		idleRight = new AnimationData();
		rightMove = new AnimationData();
		leftMove = new AnimationData();
		
		idleLeft.secsUnitNextFrame = 0;
		idleRight.secsUnitNextFrame = 0;
		rightMove.secsUnitNextFrame = 0;
		leftMove.secsUnitNextFrame = 0;
		
		
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
			}else{
				idleRight.draw();
				
			}
		}else{
			if(direction == 0)
			{
				TGAController.glDrawSprite(Window.gl, idleLeft.def.getFrameTex(0), Pos[0], Pos[1], idleLeft.def.size[0], idleLeft.def.size[1]);
			}else{
				TGAController.glDrawSprite(Window.gl, idleRight.def.getFrameTex(0), Pos[0], Pos[1], idleRight.def.size[0], idleRight.def.size[1]);
			}
		}
	}
	
	protected static boolean inBounds(int nextx, int nexty, int xbound, int ybound){
    	if(nextx >= 1 && nextx <= xbound-60 && nexty >= 1 && nexty <= ybound-45){
    		return true;
    	}
    	return false;
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
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		CameraTest.movingSpeed = this.speed;
	}
	
	
	
}
