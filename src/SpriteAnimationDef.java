
public class SpriteAnimationDef {

	String name;
	FrameDef[] frames;
	int[] size = new int[2];
	
	class FrameDef{
		int image;
		float frameTimeSecs;
		
		public FrameDef(int tex)
		{
			image = tex;
			frameTimeSecs = 199999999;
		}
	}
	
	public SpriteAnimationDef(String name, int numOfFrames, String[] tex)
	{
		frames = new FrameDef[numOfFrames];
		this.name = name;
		
		for(int i = 0; i < tex.length; i++)
		{
			frames[i] = new FrameDef(TGAController.glTexImageTGAFile(Window.gl, tex[i], size));
		}
	}
	
	public float getFrameTime(int i)
	{
		return frames[i].frameTimeSecs;
	}
	
	public int getFrameTex(int i)
	{
		return frames[i].image;
	}
	
	public String getAnimationName()
	{
		return name;
	}
}
