import java.awt.Color;
import java.awt.Font;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.util.awt.TextRenderer;


public class main {

    // Set this to true to force the game to exit.
    private static boolean shouldExit;
    
    
    public static void main(String[] args)
    {
    	Window.createWindow(640, 480, "Michael Finder's Apprentice");
    	Font font = new Font("Verdana", Font.BOLD, 24); int spacing = Window.window.getHeight()-20;
    	
    	
    	/*Initialize Keyboard*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Keyboard..", font); spacing-= 24;
    	Keyboard.initializeKb();
    	
    	
    	/*Initialize Objects*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Objects..", font); spacing-=24;
    	Player one = new Player("Kirby", 4, false);
    	Player boo = new Player("BOO", 3, true, one);
    	Level map = new Level(1);
    	
    	/*Initialize Text*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Texts..", font); spacing-=24;
    	int yOffSet = Window.window.getHeight(); int xOffSet = 0;
    	Text AiOn = new Text("AI ON"); AiOn.setColor(Color.green); AiOn.setY(480-28);
    	Text AiOff = new Text("AI OFF"); AiOff.setColor(Color.red); AiOff.setY(480-28);
    	
    	Text playerHealthTitle = new Text(one.getName()+"'S HEALTH"); 
    	playerHealthTitle.setColor(Color.blue); 
    	playerHealthTitle.setSize(18); yOffSet -= playerHealthTitle.getSize();
    	playerHealthTitle.setY(yOffSet);
    	
    	Text playersHealth = new Text("100");
    	playersHealth.setColor(Color.green);
    	playersHealth.setSize(18);
    	playersHealth.setY(yOffSet);
    	playersHealth.setX(230);
    	
    	Text enemyHealthTitle = new Text(boo.getName()+"'S HEALTH");
		enemyHealthTitle.setColor(Color.red);
		enemyHealthTitle.setSize(18); yOffSet -= enemyHealthTitle.getSize();
		enemyHealthTitle.setY(yOffSet);
		enemyHealthTitle.setX(18);
		
		Text enemysHealth = new Text("100");
		enemysHealth.setColor(Color.green);
		enemysHealth.setSize(18);
		enemysHealth.setY(yOffSet);
		enemysHealth.setX(230);
		
    	
    	
    	/*Load Textures*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Textures(heavy)..", font); spacing-=24;
    	Window.window.display();
    	String[] leftTex = {"kirby1left.tga", "kirby2left.tga", "kirby3left.tga", "kirby4left.tga"};
    	String[] rightTex = {"kirby1.tga", "kirby2.tga", "kirby3.tga", "kirby4.tga"};
    	String[] rightMovingTex = {"kirbyRMove1.tga", "kirbyRMove2.tga", "kirbyRMove3.tga","kirbyRMove4.tga","kirbyRMove5.tga","kirbyRMove6.tga","kirbyRMove7.tga","kirbyRMove8.tga","kirbyRMove9.tga","kirbyRMove10.tga"};
    	String[] leftMovingTex = {"kirbyLMove1.tga","kirbyLMove2.tga","kirbyLMove3.tga","kirbyLMove4.tga","kirbyLMove5.tga","kirbyLMove6.tga","kirbyLMove7.tga","kirbyLMove8.tga","kirbyLMove9.tga","kirbyLMove10.tga"};
    	String[] booLeftMovingTex = new String[4];
    	String[] booRightMovingTex = new String[4];
    	String[] booIdleTex = new String[4];
    	String[] fireBallTex = new String[3];
    	String[] starTex = new String[1];
    	for(int i = 0; i < booLeftMovingTex.length; i++)
    	{
    		booLeftMovingTex[i] = "booL"+(i+1)+".tga";
    		booRightMovingTex[i] = "booR"+(i+1)+".tga";
    		booIdleTex[i] = "booI"+(i+1)+".tga";
    	}
    	for(int i = 0; i < fireBallTex.length; i++)
    	{
    		fireBallTex[i] = "fireBall"+(i+1)+".tga";
    	}
    	starTex[0] = "star.tga";
    	
    	SpriteAnimationDef idleLeft = new SpriteAnimationDef("idleLeft", leftTex.length, leftTex);
    	SpriteAnimationDef idleRight = new SpriteAnimationDef("idleRight", rightTex.length, rightTex);
    	SpriteAnimationDef rightMoving = new SpriteAnimationDef("rightMoving", rightMovingTex.length, rightMovingTex);
    	SpriteAnimationDef leftMoving = new SpriteAnimationDef("leftMoving", leftMovingTex.length, leftMovingTex);
    	SpriteAnimationDef booMoveLeft = new SpriteAnimationDef("leftMoving", booLeftMovingTex.length, booLeftMovingTex);
    	SpriteAnimationDef booMoveRight = new SpriteAnimationDef("rightMoving", booRightMovingTex.length, booRightMovingTex);
    	SpriteAnimationDef booIdle = new SpriteAnimationDef("idleBoo", booIdleTex.length, booIdleTex);
    	
    	
    	rightMoving.setFrameTime(59999999); leftMoving.setFrameTime(59999999);//setting left & right speed
    	
    	
    	one.idleLeft.def = idleLeft; 
    	one.idleRight.def = idleRight; 
    	one.rightMove.def = rightMoving; 
    	one.leftMove.def = leftMoving; 
    	
    	boo.leftMove.def = booMoveLeft;
    	boo.rightMove.def = booMoveRight;
    	boo.idleTex.def = booIdle;
    	
    	/*Physics Implementation*/
    	int physicsDeltaMs = 10;
    	int lastPhysicsFrameMS = 0;
        
    	/*Extra*/
    	Projectile fireBall = new Projectile("Fireball", fireBallTex, 3);
    	Projectile star = new Projectile("Star", starTex, 4);
    	fireBall.setFrameTime(999999);
    	fireBall.setDeltaMoveTime(9);
    	star.setDeltaMoveTime(9);

        boo.setX((int) (Window.window.getWidth() * 1.2));
    	boo.setY((int) (Window.window.getHeight() * 0.3));
    	
    	fireBall.setStartingX(654);
    	fireBall.setStartingY(189);
    	fireBall.x = fireBall.startingX;
    	fireBall.y = fireBall.startingY;
    	
    	star.setStartingX(one.getX()+one.getWidth());
    	star.setStartingY(one.getY()+one.getHealth()/2);
    	
    	boo.addProjectile(fireBall);
    	one.addProjectile(star);
    	
    	
        /*Game LooOoooOooOOOooOoP*/
        System.out.println("starting loop..");
        while (!shouldExit) {
            System.arraycopy(Keyboard.getKbState(), 0, Keyboard.getKbPrevState(), 0, Keyboard.getKbState().length);

            // Actually, this runs the entire OS message pump.
            Window.window.display();
            if (!Window.window.isVisible()) {
                shouldExit = true;
                break;
            }

            /*Game Logic*/
            if (Keyboard.getKbState()[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }

            one.updateMovement();
            
            int curFrameMs = 100;
     
			/*Physics Update*/
            do{
            	Camera.boundaryUpdate();
            	one.boundaryCheck();
            	if(one.collisionCheck(map)){//later change map to a variable that points to current map
            		System.out.println("you hit a wall!");
            	}
            	boo.boundaryCheck();
            	boo.updateMovement();
            	boo.updateProjectiles();
            	boo.projectileCollision();
            	lastPhysicsFrameMS += physicsDeltaMs;
            }while(lastPhysicsFrameMS + physicsDeltaMs < curFrameMs);
            lastPhysicsFrameMS = 0;
            
            boo.updateProjectileHits();
            
            /*health text*/
            if(one.getHealth() <=100 && one.getHealth() >70)
            {
            	playersHealth.setColor(Color.GREEN);
            }else if(one.getHealth() <=70 && one.getHealth() >30)
            {
            	playersHealth.setColor(Color.yellow);
            }else if(one.getHealth() <= 30)
            {
            	playersHealth.setColor(Color.RED);
            }
            playersHealth.setText(String.valueOf(one.getHealth()));
			if(Keyboard.getKbPrevState()[KeyEvent.VK_0])
			{
				one.setHealth(100);
				System.out.println("Player "+one.name+"'s health restarted.");
			}
            
            /*Clear Past*/
            Window.clearWindow();
            
            /*Draw present*/
            map.draw();
            
            playerHealthTitle.draw();
            playersHealth.draw();
            enemyHealthTitle.draw();
            enemysHealth.draw();
            
            one.draw();
            boo.draw();
           
           
            
            
        }
        System.out.println("loop exited, bye.");
        System.exit(0);
    }


	
}
