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
    	
    	/*Initialize Keyboard*/
    	System.out.println("Initializing Keyboard..");
    	Keyboard.initializeKb();
    	
    	/*Initialize Characters*/
    	System.out.println("Initializing Characters..");
    	Player one = new Player("Mega", 5, false);
    	Player bot = new Player("Pet", 4, true, one);
    	Background bg = new Background();
    	
    	/*Initialize Text*/
    	System.out.println("Initializing Text...");
    	Text AiOn = new Text("AI ON"); AiOn.setColor(Color.green); AiOn.setY(480-12);
    	Text AiOff = new Text("AI OFF"); AiOff.setColor(Color.red); AiOff.setY(480-12);
    	Text AiSwitch = new Text("N to turn ON | M to turn OFF"); AiSwitch.setColor(Color.gray); AiSwitch.setY(480-24);
    	Text HUD = new Text("TAG"); HUD.setColor(Color.white); HUD.centerX(); HUD.setSize(24);
    
    	
    	/*Load Textures*/
    	System.out.println("Initializing Textures..");
        one.loadTexture("right","Mega-Man-transparent.tga");
        one.loadTexture("left","Mega-man-transparent-left.tga");
        bot.loadTexture("right","petRight.tga");
        bot.loadTexture("left","petLeft.tga");
        bg.loadTexture("bg.tga");
        
        TileBG tileBG = new TileBG("tile.tga");
        
        
        

    	
    	
    	
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

            // Game logic.
            if (Keyboard.getKbState()[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }
            
            /*On the run*/
            one.move();
            bot.move();
            
            /*Clear Past*/
            Window.clearWindow();
            
            /*Draw present*/
            
            bg.draw();
            tileBG.draw();
            
            if(bot.isAimoving)
            {
            	AiOn.draw();
            }else{
            	AiOff.draw();
            }
            AiSwitch.draw();
            HUD.draw();
           
            one.draw();
            bot.draw();
            
           
           
            
            
        }
        System.out.println("loop exited, bye.");
        System.exit(0);
    }
}
