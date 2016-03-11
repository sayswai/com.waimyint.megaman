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
    	Player one = new Player("Mega", 3, false);
    	Player bot = new Player("Pet", 4, true, one);
    	Level map = new Level(1);
    	
    	/*Initialize Text*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Texts..", font); spacing-=24;
    	Text AiOn = new Text("AI ON"); AiOn.setColor(Color.green); AiOn.setY(480-28);
    	Text AiOff = new Text("AI OFF"); AiOff.setColor(Color.red); AiOff.setY(480-28);
    	Text AiSwitch = new Text("N to turn ON | M to turn OFF"); AiSwitch.setColor(Color.white); AiSwitch.setY(480-40);
    	Text noClipOn = new Text("Clip ON"); noClipOn.setColor(Color.green); noClipOn.setY(480-52);
    	Text noClipOff = new Text("Clip OFF"); noClipOff.setColor(Color.red); noClipOff.setY(480-52);
    	Text noClipSwitch = new Text("ENTER to turn off clip | BACKSPACE to turn on clip"); noClipSwitch.setColor(Color.white); noClipSwitch.setY(480-64);
    
    	
    	/*Load Textures*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Textures(heavy)..", font); spacing-=24;
    	Window.window.display();
    	String[] leftTex = {"kirby1left.tga", "kirby2left.tga", "kirby3left.tga", "kirby4left.tga"};
    	String[] rightTex = {"kirby1.tga", "kirby2.tga", "kirby3.tga", "kirby4.tga"};
    	String[] rightMovingTex = {"kirbyRMove1.tga", "kirbyRMove2.tga", "kirbyRMove3.tga","kirbyRMove4.tga","kirbyRMove5.tga","kirbyRMove6.tga","kirbyRMove7.tga","kirbyRMove8.tga","kirbyRMove9.tga","kirbyRMove10.tga"};
    	String[] leftMovingTex = {"kirbyLMove1.tga","kirbyLMove2.tga","kirbyLMove3.tga","kirbyLMove4.tga","kirbyLMove5.tga","kirbyLMove6.tga","kirbyLMove7.tga","kirbyLMove8.tga","kirbyLMove9.tga","kirbyLMove10.tga"};
    	String[] botLeftIdleTex = {"petLeft.tga"};
    	String[] botRightIdleTex = {"petRight.tga"};
    	
    	SpriteAnimationDef idleLeft = new SpriteAnimationDef("idleLeft", leftTex.length, leftTex);
    	SpriteAnimationDef idleRight = new SpriteAnimationDef("idleRight", rightTex.length, rightTex);
    	SpriteAnimationDef rightMoving = new SpriteAnimationDef("rightMoving", rightMovingTex.length, rightMovingTex);
    	SpriteAnimationDef leftMoving = new SpriteAnimationDef("leftMoving", leftMovingTex.length, leftMovingTex);
    	SpriteAnimationDef botIdleLeft = new SpriteAnimationDef("idleLeft", botLeftIdleTex.length, botLeftIdleTex);
    	SpriteAnimationDef botIdleRight = new SpriteAnimationDef("idleRight", botRightIdleTex.length, botRightIdleTex);
    	rightMoving.setFrameTime(59999999); leftMoving.setFrameTime(59999999);//setting left & right speed
    	
    	one.idleLeft.def = idleLeft; 
    	one.idleRight.def = idleRight; 
    	one.rightMove.def = rightMoving; 
    	one.leftMove.def = leftMoving; 
    	
    	bot.idleLeft.def = botIdleLeft; 
    	bot.idleRight.def = botIdleRight; 
    	
    	/*Physics Implementation*/
    	
        
        

    	
    	
    	
        /*Game LOOOOOOP*/
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
            

            one.update();

            //bot.update();
            
            /*Clear Past*/
            Window.clearWindow();
            
            /*Draw present*/
            map.draw();
            
            if(bot.isAimoving)
            {
            	AiOn.draw();
            }else{
            	AiOff.draw();
            }
            
            if(!one.noClip)
            {
            	noClipOn.draw();
            }else{
            	noClipOff.draw();
            }
            noClipSwitch.draw();
           // AiSwitch.draw();
           
             one.draw();
             //bot.draw();
            
           
           
            
            
        }
        System.out.println("loop exited, bye.");
        System.exit(0);
    }
}
