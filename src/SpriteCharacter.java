
public class SpriteCharacter {

	/*Position of sprite (x, y)*/
	protected int[] Pos = new int[]{10, 10};
	
	/*Texture of Sprite*/
	protected int rightTex, leftTex, upTex, downTex;
	protected int currentTex;
	
	/*Size of sprite*/
	protected int[] Size = new int[2];
	

	
	/*Player Statistics*/
	protected String name;
	protected int speed;
	protected boolean isAi = false;
	protected boolean isAimoving = false;
	protected boolean inJump = false;
	protected boolean beginJump = false;
	protected boolean endJump = false;
	protected int	jumpVal = 0;
	
	public SpriteCharacter(String name, int speed, boolean isAi)
	{
		this.name = name;
		this.speed = speed;
		this.isAi = isAi;
	}
	
	public void loadTexture(String direction, String texture)
	{
		 
		if(direction.equals("up")){
			upTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
			currentTex = upTex;
		}else if(direction.equals("down")){
			downTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
			currentTex = downTex;
		}else if(direction.equals("right")){
			rightTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
			currentTex = rightTex;
		}else{
			leftTex = TGAController.glTexImageTGAFile(Window.gl, texture, Size);
			currentTex = leftTex;
		}
		
	}
	
	public void draw()
	{
		TGAController.glDrawSprite(Window.gl, currentTex, Pos[0], Pos[1], Size[0]+10, Size[1]+10);
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
