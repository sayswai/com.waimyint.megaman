import com.jogamp.newt.event.KeyEvent;



public class AI extends SpriteCharacter {
	
	/* Ai Status */
	private boolean AiState = false;

	public AI(String name, int speed) {
		super(name, speed);
		Pos[0] = 0;
		Pos[1] = Window.window.getHeight()-100;
	}
	
	
	public void draw(Player player)
	{
		if(Keyboard.getKbState()[KeyEvent.VK_M])
        {AiState = false;}
        if(Keyboard.getKbState()[KeyEvent.VK_N])
        {AiState = true;}
        
        super.draw();
        
        if(AiState){
        	update(player);
        }
        
	}
	
	public void update(Player player)
	{
		
		if(Pos[0] < player.getPos()[0])
		{
			if(checkAiBounds(player)){
			Tex = 1;
			Pos[0] += speed;
			}
		}
		if(Pos[0] > player.getPos()[0])
		{
			if(checkAiBounds(player))
			{
			Tex = 2;
			Pos[0] -= speed;
			}
		}
		if(Pos[1] < player.getPos()[1])
		{
			if(checkAiBounds(player)){
			Pos[1] += speed;
			}
		}
		if(Pos[1] > player.getPos()[1])
		{
			if(checkAiBounds(player)){
			Pos[1] -= speed;
			}
		}
		
	}

	private boolean checkAiBounds(Player player) {
			
			//x-bound
			if((Pos[0] < player.getPos()[0]-50 || Pos[0] > player.getPos()[0]+player.getSize()[0]+20)){
				
				return true;
			}
			return false;
	}

}

	
	