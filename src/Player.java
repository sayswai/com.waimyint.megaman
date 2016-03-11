import com.jogamp.newt.event.KeyEvent;




public class Player extends SpriteCharacter {
	
	private Player leader;
	
	public Player(String name, int speed, boolean isAi)
	{
		super(name, speed, isAi);
		
		 /*Positioning Sprites Spawn point here*/
        Pos[0] = Window.window.getWidth()/2;
        Pos[1] = 380;
	}
	public Player(String name, int speed, boolean isAi, Player player)
	{
		super(name, speed, isAi);
		
		leader = player;
		 /*Positioning Sprites Spawn point here*/
		Pos[0] = 60;
		Pos[1] = 380;
	}
	
	public void update()
	{
		if(!isAi){
			if(Keyboard.getKbPrevState()[KeyEvent.VK_ENTER])
			{
				noClip = true;
			}
			if(Keyboard.getKbPrevState()[KeyEvent.VK_BACK_SPACE])
			{
				if(Pos[1] <= 280)
				{
					Pos[1] = 300;
				}
				noClip = false;
			}
			//if (Keyboard.getKbState()[KeyEvent.VK_A] && inBounds(Pos[0]-speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//left
			if (Keyboard.getKbState()[KeyEvent.VK_A]){
					Pos[0] -= speed;
					
					/*Camera Update*/
					if(Pos[0] < (Window.window.getWidth() * 1.50)){//Initiate camera scroll
						if((Camera.x - speed) >= 0){//Check to see if camera will go past the left side
							Camera.x -= speed;
						}else{//reached the left end
							Camera.x -= Camera.x;
						}
					}
	            direction = 0;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_D]) {//right
	    	   Pos[0] += speed;
	       	  
	       	  /*Camera Update*/
	       	  if(Pos[0] > Window.window.getWidth()/2){//Scroll camera to the right only if Character is past the first-mid mark
	       		  if((Camera.x + speed) < Window.window.getWidth()){
	       			  Camera.x += speed;
	       		  }else{
	       			  int delta = Window.window.getWidth() - Camera.x;
	       			  Camera.x += delta;
	       		  }
	       	  }
			 
	       	  direction = 1;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_W]) {//up
	    	  Pos[1] -= speed;
	          //CameraTest.y -= speed;
	          
	       }
	       if (Keyboard.getKbState()[KeyEvent.VK_S]) {//down
	           Pos[1] += speed;
	           //CameraTest.y += speed;
	       }
	       
	       rPos[0] = Pos[0] - Camera.x;
	       rPos[1] = Pos[1] - Camera.y;
	       /*Boundary Checks*/
	       if((rPos[0]+idleLeft.curFrameSize[0]) > Window.window.getWidth())
	       {
	    	   int delta = rPos[0] - (Window.window.getWidth() - idleLeft.curFrameSize[0]);
	    	   rPos[0] -= delta;
	    	   Pos[0] -= delta;
	       }
	       if(rPos[0] < 0)
	       {
	    	   int delta = -rPos[0];
	    	   rPos[0] += delta;
	    	   Pos[0] += delta;
	       }
	       if((rPos[1] + idleLeft.curFrameSize[1]+10) > Window.window.getHeight())
	       {
	    	  rPos[1] = Window.window.getHeight() - idleLeft.curFrameSize[1] - 10;
	    	   Pos[1] = Window.window.getHeight() - idleLeft.curFrameSize[1] - 10;
	       }
	       if(rPos[1] < 0)
	       {
	    	   rPos[1] = 0;
	    	   Pos[1] += 0;
	       }
	       System.out.println("rPosX: "+rPos[0]+" S.X: "+Pos[0]+" C.X: "+Camera.x);
	       //System.out.println("rPoY: "+rPos[1] +" S.Y: "+Pos[1]+" C.Y: "+CameraTest.y);
	       
	       
	       idleLeft.update(); idleRight.update();
	       //System.out.println("X: " +Pos[0]+ " Y: "+Pos[1]);
	              
	       /*
	       if(inJump)
	       {
	    	   jump();
	       }
	       if(Keyboard.getKbState()[KeyEvent.VK_SPACE]&& inJump)
	       {
	    	   jump();
	       }
	       if(Keyboard.getKbState()[KeyEvent.VK_SPACE] && inBounds(Pos[0], Pos[1]+speed, Window.window.getWidth(), Window.window.getHeight()-10)){
	    	   inJump = true;
	    	   beginJump = true;
	    	   jump();
	       }
	       */
	       
		
		}else{

			if(Keyboard.getKbState()[KeyEvent.VK_N])
			{isAimoving = true;}
			if(Keyboard.getKbState()[KeyEvent.VK_M])
			{isAimoving = false;}
			
			
			if(isAimoving){
			AIcontroller.move(this, leader);
			}
			
		}
	}
	
	/*
	private void jump()
	{
		if((jumpVal <= speed+20) && beginJump)
		{
			Pos[1] -= 5;
			jumpVal += 5;
			
		}else if(endJump && (jumpVal <= speed+20) && (jumpVal >= 0)){
			Pos[1]+= 5;
			jumpVal-= 5;
		}else if(beginJump)
		{
			jumpVal-=5;
			Pos[1]+=5;
			endJump = true;
			beginJump = false;
		}else if(endJump){
			inJump = false;
			beginJump = false;
			endJump = false;
			jumpVal = 0;
		}
	}*/
	public int getX() {
		return Pos[0];
	}
	public int getY(){
		return Pos[1];
	}
	
	
	
	
	
}
