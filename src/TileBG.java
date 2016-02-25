
public class TileBG {

	private int[][] bg = new int[12][16];
	private int[]	tileSize = new int[2];
	
	public TileBG(String tile)
	{
		bg[0][0] = TGAController.glTexImageTGAFile(Window.gl, tile, tileSize);
	
		for(int i = 0; i<bg.length; i++)
		{
			for(int j = 1; j<bg[0].length; j++)
			{
				bg[i][j] = bg[0][0];
			}
		}
	
	}
	
	
	public void draw()
	{
		drawFloor();
	}
	
	private void drawFloor()
	{
		
		/*bottom floor*/
			for(int j = 0; j<bg[0].length; j++)
			{
				TGAController.glDrawSprite(Window.gl, bg[0][0], j*40, 480-40, tileSize[0], tileSize[1]);
			}
			
		/*second to bottom floor*/
			for(int i = 6; i<bg[0].length; i++)
			{
				TGAController.glDrawSprite(Window.gl, bg[0][i], i*40, 480-80, tileSize[0], tileSize[1]);
			}
			
			for(int i = 9; i<bg[0].length; i++)
			{
				TGAController.glDrawSprite(Window.gl, bg[0][i], i*40, 480-120, tileSize[0], tileSize[1]);
				
			}
	
	}
	
	
}
