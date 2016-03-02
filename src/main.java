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
    	
    	/*Initialize Characters*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Characters..", font); spacing-=24;
    	Player one = new Player("Mega", 5, false);
    	Player bot = new Player("Pet", 4, true, one);
    	Background bg = new Background();
    	
    	/*Initialize Text*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Texts..", font); spacing-=24;
    	Text AiOn = new Text("AI ON"); AiOn.setColor(Color.green); AiOn.setY(480-52);
    	Text AiOff = new Text("AI OFF"); AiOff.setColor(Color.red); AiOff.setY(480-52);
    	Text AiSwitch = new Text("N to turn ON | M to turn OFF"); AiSwitch.setColor(Color.white); AiSwitch.setY(480-64);
    	Text HUD = new Text("TAG"); HUD.setColor(Color.white); HUD.centerX(); HUD.setSize(24);
    
    	
    	/*Load Textures*/
    	TextController.glWrite(Color.white, 0, spacing, "Initializing Textures...", font); spacing-=24;
        one.loadTexture("right","kirby2.tga");
        one.loadTexture("left","kirby2left.tga");
        bot.loadTexture("right","petRight.tga");
        bot.loadTexture("left","petLeft.tga");
        bg.loadTexture("bg.tga");
        
        TileBG tiles = new TileBG(1);
        
        
        

    	
    	
    	
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
            
            one.move();
            bot.move();
            
            /*Clear Past*/
            Window.clearWindow();
            
            /*Draw present*/
            bg.draw();
            tiles.draw();
            
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
