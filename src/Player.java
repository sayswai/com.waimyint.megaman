import com.jogamp.newt.event.KeyEvent;




public class Player extends SpriteCharacter {
	
	private Player leader;
	
	public Player(String name, int speed, boolean isAi)
	{
		super(name, speed, isAi);
		
		 /*Positioning Sprite to be in the middle*/
        Pos[0] = Window.window.getWidth()/2;
        Pos[1] = Window.window.getHeight()/2;
	}
	public Player(String name, int speed, boolean isAi, Player player)
	{
		super(name, speed, isAi);
		
		leader = player;
		
		Pos[0] = 0;
		Pos[1] = 420;
	}
	
	public void move()
	{
		if(!isAi){
			if (Keyboard.getKbState()[KeyEvent.VK_A] && inBounds(Pos[0]-speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//left
	            Pos[0] -= speed;
	            position = 0;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_D] && inBounds(Pos[0]+speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//right
	       	  Pos[0] += speed;
	       	  position = 1;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_W] && inBounds(Pos[0], Pos[1]-speed, Window.window.getWidth(), Window.window.getHeight())) {//up
	           Pos[1] -= speed;
	       }
	
	       if (Keyboard.getKbState()[KeyEvent.VK_S] && inBounds(Pos[0], Pos[1]+speed, Window.window.getWidth(), Window.window.getHeight()-10)) {//down
	           Pos[1] += speed;
	       }
	       idleLeft.update(); idleRight.update();
	       
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
			if(isAimoving){
			AIcontroller.move(this, leader);
			}
			if(Keyboard.getKbState()[KeyEvent.VK_N])
			{isAimoving = true;}
			if(Keyboard.getKbState()[KeyEvent.VK_M])
			{isAimoving = false;}
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
	
	
}
