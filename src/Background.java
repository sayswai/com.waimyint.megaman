
public class Background {

	/* Background Texture */
    private static int bgTex;

    /* Size of the Background */
    private static int[] bgSize = new int[2];
    
    public Background()
    {}
    
    public void loadTexture(String tex)
    {
    	bgTex = TGAController.glTexImageTGAFile(Window.gl, tex, bgSize);
        
    }
    
    public void draw()
    {
    	TGAController.glDrawSprite(Window.gl, bgTex, 0, 0, bgSize[0], bgSize[1]);
    }
}
