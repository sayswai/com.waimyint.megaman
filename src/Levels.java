
public class Levels {

	private int tileWidth;
	private int tileHeight;
	private int maxTilesX;
	private int maxTilesY;
	
	private int floorTex, ceilingTex;
	private int[] texTile = new int[2];
	
	private Tiles tile;
	
	public Levels(int tileWidth, int tileHeight)
	{
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		maxTilesX = Window.window.getWidth() / tileWidth;
		maxTilesY = Window.window.getHeight() / tileHeight;
		initiateTiles();
	}
	
	public int retrieveTilePos(int x, int y)
	{
		return tile.getTile(x, y);
	}
	
	public Tiles getTile()
	{
		return tile;
	}
	
	public int totalTiles()
	{
		return tile.totalTiles();
	}
	
	private void initiateTiles() {
		tile = new Tiles(maxTilesX, maxTilesY, tileWidth, tileHeight); 
		loadCeiling();
		loadFloor();
		
		
	}

	private void loadCeiling() {
		ceilingTex = TGAController.glTexImageTGAFile(Window.gl, "tile.tga", texTile);
		for(int i = 0; i<maxTilesX; i++)
		{
			tile.setTile(i, 0, ceilingTex);
		}
	}

	private void loadFloor() {
		floorTex = TGAController.glTexImageTGAFile(Window.gl, "tile.tga", texTile);
		
		for(int i = 0; i<maxTilesX; i++)
		{
			tile.setTile(i, maxTilesY-1, floorTex);
		}
		for(int i = 5; i<maxTilesX; i++)
		{
			tile.setTile(i, maxTilesY-2, floorTex);
		}
		
		for(int i = 4; i<maxTilesX; i++)
		{
			tile.setTile(i, maxTilesY-3, floorTex);
		}
		
		for(int i = 8; i<maxTilesX; i++)
		{
			tile.setTile(i, maxTilesY-4, floorTex);
		}
		
	
	}
	
	public void drawLevel()
	{
		for(int y = 0; y < maxTilesY; y++)
		{
			for(int x = 0; x < maxTilesX; x++)
			{
				if(tile.getTile(x, y) == floorTex || tile.getTile(x, y) == ceilingTex)
				TGAController.glDrawSprite(Window.gl, tile.getTile(x, y), x*40, y*40, tileWidth, tileHeight);
			}
		}
	}

	

	public int getMaxTilesX() {
		return maxTilesX;
	}

	public void setMaxTilesX(int maxTilesX) {
		this.maxTilesX = maxTilesX;
	}

	public int getMaxTilesY() {
		return maxTilesY;
	}

	public void setMaxTilesY(int maxTilesY) {
		this.maxTilesY = maxTilesY;
	}
	
	
	



	
}
