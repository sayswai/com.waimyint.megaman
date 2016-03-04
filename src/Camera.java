import com.jogamp.newt.event.KeyEvent;


public class Camera {

	//private int currentBG;
	static int started = 0;
	static int scrolling = 0;
	static int spriteXrelWindow = 0;
	static int spriteX = 0; // sprite x relative to window
	//static int spriteY = 0;
	static int minbgX = 0;
	static int maxbgX = 0;
	static int deltaXtiles = 0;
	static int deltaXpixels = 0;
	//static int bgY = 0;
	
	
	public static void initialize(Player player, Level level)
	{
			started = 1;
			spriteX = player.getX();
			//spriteY = sY;
			minbgX = 0;
			maxbgX = Window.window.getWidth() / (level.getCurrentLevel().getTileWidth());

			//bgY = bY;
	}
	
	public static void update(Player player, Level level)
	{
		if(started != 0)
		{
			if(Keyboard.getKbPrevState()[KeyEvent.VK_RIGHT]){
				
			}else if(Keyboard.getKbPrevState()[KeyEvent.VK_LEFT]){
			}
			if(Keyboard.getKbState()[KeyEvent.VK_D]){
				updateRight(player, level);
			}else if(Keyboard.getKbState()[KeyEvent.VK_A]){
				updateLeft(player, level);
			}
			
		}else{
			initialize(player, level);
		}
	}

	private static void updateLeft(Player player, Level level) {
		if((player.getX() < (Window.window.getWidth( ) * .20)) && scrolling == 1 )
		{
			updateDelta(0, player, level);
			
				if(deltaXtiles >= 0){
					int comp = level.getMaxTilesX() - (Window.window.getWidth()/level.getTileWidth());
					if(deltaXtiles < comp){
						minbgX = deltaXtiles;
						maxbgX = deltaXtiles + (Window.window.getWidth() / (level.getCurrentLevel().getTileWidth()));
						
						player.setX((spriteX-deltaXpixels)+40);
					}else{//reaches the end of the map, do not change minbgx anymore & let player move freely
							minbgX = comp;
							maxbgX = comp*2;
					}
					if(deltaXtiles == 0)//stops the world from scrolling 
					{
						scrolling = 0;
					}
					}
				
				
			
		}
	}

	
	private static void updateRight(Player player, Level level) {
		if((player.getX() > (Window.window.getWidth( ) * .60)) && scrolling == 0 )
		{
			updateDelta(1, player, level);
			scrolling = 1;
		}else if(player.getX() > (Window.window.getWidth() * .70) && scrolling == 1){
			if(deltaXtiles < level.getMaxTilesX() && deltaXpixels < Window.window.getWidth())//this check prevents deltaXpixels to keep updating once it's the end of the map
			{
				updateDelta(1, player, level);
			}
		}
		
		if(deltaXtiles > 0){
			int comp = level.getMaxTilesX() - (Window.window.getWidth()/level.getTileWidth());
			if(deltaXtiles < comp){
				minbgX = deltaXtiles;
				maxbgX = deltaXtiles + (Window.window.getWidth() / (level.getCurrentLevel().getTileWidth()));
				
				player.setX((spriteX-deltaXpixels)+40);
			}else{//reaches the end of the map, do not change minbgx anymore & let player move freely
					minbgX = comp;
					maxbgX = comp*2;
				}
			}
	}

	private static void updateDelta(int i, Player player, Level level) {
		if(i==1){
			deltaXpixels += player.getSpeed();
		}else{
			deltaXpixels -= player.getSpeed();
		}

			deltaXtiles = deltaXpixels / (level.getCurrentLevel().getTileWidth());
		
		System.out.println("deltaXTiles: " +deltaXtiles+" minbgX: "+minbgX+" deltaXpixels: "+deltaXpixels);
	}
	
	
	
}
