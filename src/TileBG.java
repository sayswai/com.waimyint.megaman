
public class TileBG {

	private Levels[] levels;
	
	public TileBG(int numberOfLevels)
	{
		initiateLevels(numberOfLevels);
	}

	private void initiateLevels(int numberOfLevels) {
		levels = new Levels[numberOfLevels];
		for(int i = 0; i< numberOfLevels; i++)
		{
			levels[i] = new Levels(40, 40);
		}
	}
	/*
	public void initializeCeiling()
	{
		int maxX = levels[0].getMaxTilesX();
		for(int i = 0; i<levels.length; i++)
		{
			int[][] top = new int[2][maxX];
			for(int j = 0; )
		}
	}*/
	public void draw()
	{
		levels[0].drawLevel();
	}
	
	
	
	
	
}
