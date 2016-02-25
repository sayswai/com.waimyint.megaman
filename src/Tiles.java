
public class Tiles {

	int width;
	int height;
	private int[] tiles;
	
	public Tiles (int width, int height)
	{
		this.width = width; this.height = height;
		tiles = new int[height * width + width];
	}
	
	public int getTile(int x, int y)
	{
		return tiles[y*width + x];
	}
}
