
public class SpriteCharacter {

	/*Position of sprite (x, y)*/
	protected int[] Pos = new int[]{10, 10};
	
	/*Texture of Sprite*/
	protected int Tex;
	
	/*Size of sprite*/
	protected int[] Size = new int[2];
	

	
	/*Player Statistics*/
	protected String name;
	protected int speed;
	
	public SpriteCharacter(String name, int speed)
	{
		this.name = name;
		this.speed = speed;
	}
	
	public void loadTexture(String texture)
	{
		Tex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
	}
	
	public void draw()
	{
		TGAController.glDrawSprite(Window.gl, Tex, Pos[0], Pos[1], Size[0]+10, Size[1]+10);
	}
	
	protected static boolean inBounds(int nextx, int nexty, int xbound, int ybound){
    	if(nextx >= 1 && nextx <= xbound-35 && nexty >= 1 && nexty <= ybound-45){
    		return true;
    	}
    	return false;
    }

	public int[] getPos() {
		return Pos;
	}

	public void setPos(int[] pos) {
		Pos = pos;
	}
	 
	

	public int[] getSize() {
		return Size;
	}

	public void setSize(int[] size) {
		Size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
