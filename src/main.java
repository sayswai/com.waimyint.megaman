import com.jogamp.newt.event.KeyEvent;


public class main {

    // Set this to true to force the game to exit.
    private static boolean shouldExit;
    
    
    public static void main(String[] args)
    {
    	Window.createWindow(640, 480, "Michael Finder's Apprentice");
    	
    	/*Initialize Keyboard*/
    	Keyboard.initializeKb();
    	
    	/*Initialize Characters*/
    	Player one = new Player("Mega", 5);
    	AI bot = new AI("BOT", 2);
    	Background bg = new Background();
    	
    	
    	/*Load Textures*/
        one.loadTexture("Mega-Man-transparent.tga");
        one.loadTexture("Mega-man-transparent-left.tga");
        bot.loadTexture("Mega-Man-transparent.tga");
        bot.loadTexture("Mega-man-transparent-left.tga");
        bg.loadTexture("bg.tga");
        
        /*Game LOOOOOOP*/
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
            
            /*Clear Past*/
            Window.clearWindow();
            
            /*Draw present*/
            bg.draw();
            bot.draw(one);
            one.draw();
            
            
        }
        System.exit(0);
    }
}
