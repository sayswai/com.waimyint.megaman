
import java.awt.Rectangle;

import com.jogamp.newt.event.KeyEvent;




public class Player extends SpriteCharacter {
	
	 Player leader;
	
	public Player(String name, int speed, boolean isAi)
	{
		super(name, speed, isAi);
		
		 /*Positioning Sprites Spawn point here*/
        Pos[0] = 20;
        Pos[1] = 380;
	}
	public Player(String name, int speed, boolean isAi, Player player, int aiMode)
	{
		super(name, speed, isAi);
		
		leader = player;
		
		this.aiMode = aiMode;
		 /*Positioning Sprites Spawn point here*/
		Pos[0] = 60;
		Pos[1] = 380;
	}
	
	public void updateMovement()
	{
		lastFrameTime = System.nanoTime();
		
		if(isGrounded){
			yVelocity = 0;
			if(Keyboard.getKbPrevState()[KeyEvent.VK_SPACE]){
				//yVelocity = speed;
				//isGrounded = false;
			}

			prevPos[0] = Pos[0];
			prevPos[1] = Pos[1];
			
			if(!isAi){
				/*save last frame's positions*/
				Camera.prevX = Camera.x;
				Camera.prevY = Camera.y;
				
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
	
				if (Keyboard.getKbState()[KeyEvent.VK_A]){//left
						Pos[0] -= speed;
						if(Pos[0] < (Window.window.getWidth() * 1.50)){
						/*
						 * This "if" check allows the character to move to the center
						 * before the camera moves.
						 */
							Camera.x -= speed;
						}
		            direction = 0;
		            curr = rightMove;
		       }
		
		       if (Keyboard.getKbState()[KeyEvent.VK_D]) {//right
		    	   Pos[0] += speed;
		    	   if(Pos[0] > Window.window.getWidth()/2){
		    		   /*
						 * This "if" check allows the character to move to the center
						 * before the camera moves.
						 */
		    		   Camera.x += speed;
		    	   }
				 
		       	  direction = 1;
		       	  curr = leftMove;
		       }
		
		       if (Keyboard.getKbState()[KeyEvent.VK_W]) {//up
		    	   if(!noClip){
		    		   if((Pos[1] - speed) > 275){
		    		  Pos[1] -= speed;
		    		   }
		    	   }else{
		    		   Pos[1] -= speed;
		    	   }
		          //CameraTest.y -= speed;
		          
		       }
		       if (Keyboard.getKbState()[KeyEvent.VK_S]) {//down
		           Pos[1] += speed;
		           //CameraTest.y += speed;
		       }
		       
		       updaterPos();
		       idleLeft.update(); idleRight.update();
		     }else{
		    	 if(aiMode == 0){//ai mode - 0 {stay still}
			       updaterPos();
			       idleTex.update();
			       curr = idleTex;
					
				if(Keyboard.getKbState()[KeyEvent.VK_N])
				{isAimoving = true;}
				if(Keyboard.getKbState()[KeyEvent.VK_M])
				{isAimoving = false;}
				
				/*
				if(isAimoving){
				AIcontroller.move(this, leader);
				}
				*/
		    	 }else if(aiMode == 1){//ai mode - 1 {chasing}
		    		 if(Pos[0] < leader.getX())
		    		 {
		    			 // bot is left of the character and bot is moving right
		    			 Pos[0] += speed;
		    			 direction = 1;
		    			 curr = rightMove;
		    		 }
		    		 if(Pos[0] > leader.getX())
		    		 {
		    			 // bot is right of the character and bot is moving left
		    			 Pos[0] -= speed;
		    			 direction = 0;
		    			 curr = leftMove;
		    		 }
		    		 if(leader.getY() > Pos[1])
		    		 {
		    			 //bot is above the character and bot is moving down
		    			 Pos[1] += speed;
		    			 curr = idleTex;
		    		 }
		    		 if(leader.getY() < Pos[1])
		    		 {
		    			 //bot is below the character and bot is moving up
		    			 Pos[1] -= speed;
		    			 curr = idleTex;
		    		 }
		    		 updaterPos();
		    		 rightMove.update(); leftMove.update(); idleTex.update();
		    		 shape.move(getX(), getY());
		 			shape.resize(getWidth(), getHeight());
		    	 }
		     }
		
		}else{
			
		}
	}
	
	public void stepBack()
	{
		Pos[0] = prevPos[0];
		Pos[1] = prevPos[1];
		Camera.x = Camera.prevX;
		Camera.y = Camera.prevY;
		

	    updaterPos();
		
	}
	public void boundaryCheck(){
		/*Boundary Check*/
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
	       
	       /*Update Shape*/
			shape.move(getX(), getY());
			shape.resize(getWidth(), getHeight());
		

	       /*Updates onScreen boolean to make sure things off screen aren't being drawn*/
	       if(rPos[0] >= 0 && rPos[0] <= Window.window.getWidth())
	       {
	    	   if(rPos[1] >= 0 && rPos[1] <= Window.window.getHeight())
	    	   {
	    		   onScreen = true;
	    	   }else{
	    		   onScreen = false;
	    	   }
	       }else{
	    	   onScreen = false;
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
	public Player getLeader() {
		return leader;
	}
	public void setLeader(Player leader) {
		this.leader = leader;
	}
	public void updaterPos()
	{
		 rPos[0] = Pos[0] - Camera.x;
	     rPos[1] = Pos[1] - Camera.y;
	}
	public void updateProjectiles() {
		for(int i = 0 ; i < projectiles.length; i++)
		{

			projectiles[i].updateFrame();
			
			if(!projectiles[i].inMotion)
			{
				
				projectiles[i].endX = leader.getX()+leader.getWidth()/2;
				projectiles[i].endY = leader.getY()+leader.getHeight()/2;
				projectiles[i].inMotion = true;
				projectiles[i].setLastMoveTime(System.nanoTime());
			}else{
					projectiles[i].move();
				}
			}
		}
	public void projectileCollision() {
		for(int i = 0 ;i < projectiles.length; i++)
		{
			if(projectiles[i].overlap(leader.shape))//hits22
			{
				if(projectiles[i].targetHit == false){
					projectiles[i].targetHit = true;
					projectiles[i].restart();
				}
			}
		}
	}
	public void updateProjectileHits() {
		if(projectiles[0].targetHit)
		{
			leader.health -= 2;
			projectiles[0].targetHit = false;
		}
	}
	public boolean mapCollisionCheck(Level map) {
		
		int[] topPhysics = new int[2];
		int[] bottomPhysics = new int[2];
		
		double leftX = Math.ceil((double) (Pos[0]-30) / map.getTileWidth());//-30 for offset to perfect contact
		double rightX = Math.ceil((double) (Pos[0]+22) / map.getTileWidth());//+22 for offset to perfect contact
		double topY = Math.ceil((double) (Pos[1]-30) / map.getTileHeight());
		double bottomY = Math.ceil((double) (Pos[1]+13) / map.getTileHeight());
		
		topPhysics[0] = map.getCurrentLevel().getTilePhysics((int) leftX,(int) topY);
		topPhysics[1] = map.getCurrentLevel().getTilePhysics((int) rightX,(int) topY);

		
		bottomPhysics[0] = map.getCurrentLevel().getTilePhysics((int) leftX,(int) bottomY); 
		bottomPhysics[1] = map.getCurrentLevel().getTilePhysics((int) rightX,(int) bottomY);
		
		boolean overlap = false;
		for(int i = 0; i < 2 && !overlap; i++)
		{
			if(topPhysics[i] == 1 || bottomPhysics[i] == 1)
			{
				overlap = true;
			}
		}
		if(overlap)
			stepBack();
		
		return overlap;
	}
	
	
}
