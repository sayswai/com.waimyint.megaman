import com.jogamp.newt.event.KeyEvent;



public class Player extends SpriteCharacter {
	public Player(String name, int speed)
	{
		super(name, speed);
		
		 /*Positioning Sprite to be in the middle*/
        Pos[0] = Window.window.getWidth()/2;
        Pos[1] = Window.window.getHeight()/2;
	}
	
	public void move()
	{
		if (Keyboard.getKbState()[KeyEvent.VK_A] && inBounds(Pos[0]-speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//left
            Pos[0] -= speed;
            Tex = 2;
       }

       if (Keyboard.getKbState()[KeyEvent.VK_D] && inBounds(Pos[0]+speed, Pos[1], Window.window.getWidth(), Window.window.getHeight())) {//right
       	  Pos[0] += speed;
       	  Tex = 1;
       }

       if (Keyboard.getKbState()[KeyEvent.VK_W] && inBounds(Pos[0], Pos[1]-speed, Window.window.getWidth(), Window.window.getHeight())) {//up
           Pos[1] -= speed;
       }

       if (Keyboard.getKbState()[KeyEvent.VK_S] && inBounds(Pos[0], Pos[1]+speed, Window.window.getWidth(), Window.window.getHeight()-10)) {//down
           Pos[1] += speed;
       }
	}

    	
}
