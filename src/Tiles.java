import java.awt.Shape;


public class Tiles {

	private int width;
	private int height;
	
	private int maxX;
	private int maxY;
	private int[] tiles;
	private int totalTiles;
	
	private Shape shape;
	
	public Tiles (int maxX, int maxY, int width, int height)
	{
		this.width = width; this.height = height;
		this.maxX = maxX; this.maxY = maxY;
		tiles = new int[maxY * width + maxX];
		this.totalTiles = tiles.length;
	}
	
	public int getTile(int x, int y)
	{
		return tiles[y*width + x];
	}
	
	public int getTileWidth(int x, int y)
	{
		return width;
	}
	
	public int getTileHeight(int x, int y)
	{
		return height;
	}
	
	public void setTile(int x, int y, int tex)
	{
		tiles[y*width + x] = tex;
	}
	
	public int totalTiles()
	{
		return totalTiles;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	
	
	
	
	
}
