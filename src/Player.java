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
			if (Keyboard.getKbState()[KeyEvent.VK_A] && inBounds(Pos[0]-speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//left
	            Pos[0] -= speed;
	            Camera.spriteX -= speed;
	            direction = 0;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_D] && inBounds(Pos[0]+speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//right
	       	  Pos[0] += speed;
			  Camera.spriteX += speed;
	       	  direction = 1;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_W] && inBounds(Pos[0], Pos[1]-speed, Window.window.getWidth(), Window.window.getHeight())) {//up
	          if(!noClip){
	    	   if (Pos[1] > 280)
	        	  Pos[1] -= speed;
	          }else{
	        	  Pos[1] -= speed;
	          }
	          
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_S] && inBounds(Pos[0], Pos[1]+speed, Window.window.getWidth(), Window.window.getHeight()-10)) {//down
	           Pos[1] += speed;
	       }
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
	}
	public int getX() {
		return Pos[0];
	}
	public int getY(){
		return Pos[1];
	}
	
	
	
	
}
