/**
 * Created by jared on 2/9/16.
 */

//import javax.media.nativewindow.WindowClosingProtocol;
//import javax.media.opengl.*;

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;

public class main {
    // Set this to true to force the game to exit.
    private static boolean shouldExit;

    // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];

    // Position of the sprite.
    private static int[] spritePos = new int[] { 10, 10 };

    // Texture for the sprite.
    private static int spriteTex;

    // Size of the sprite.
    private static int[] spriteSize = new int[2];

    /**
     * Checks to see if sprite is in bounds of window
     * @return true if in bounds \ false if out of bounds
     */
    private static boolean inBounds(int nextx, int nexty, int xbound, int ybound){
    	if(nextx >= 1 && nextx <= xbound-35 && nexty >= 1 && nexty <= ybound-45){
    		return true;
    	}
    	return false;
    }
    public static void main(String[] args) {
        GLProfile gl2Profile;

        try {
            // Make sure we have a recent version of OpenGL
            gl2Profile = GLProfile.get(GLProfile.GL2);
        }
        catch (GLException ex) {
            System.out.println("OpenGL max supported version is too low.");
            System.exit(1);
            return;
        }

        // Create the window and OpenGL context.
        GLWindow window = GLWindow.create(new GLCapabilities(gl2Profile));
        window.setSize(640, 480);
        window.setTitle("MINIMAN");
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
        //window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent keyEvent) {
                kbState[keyEvent.getKeyCode()] = true;
            }

            public void keyReleased(KeyEvent keyEvent) {
                kbState[keyEvent.getKeyCode()] = false;
            }
            
        });

        // Setup OpenGL state.
        window.getContext().makeCurrent();
        GL2 gl = window.getGL().getGL2();
        gl.glViewport(0, 0, 640, 480);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glOrtho(0, 640, 480, 0, 0, 100);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        // Load the texture.
        spriteTex = glTexImageTGAFile(gl, "Mega-Man-transparent.tga", spriteSize);
        spriteTex = glTexImageTGAFile(gl, "Mega-man-transparent-left.tga", spriteSize);
        
        /*Positioning Sprite to be in the middle*/
        spritePos[0] = window.getWidth()/2;
        spritePos[1] = window.getHeight()/2;
        
        // The game loop
        while (!shouldExit) {
            System.arraycopy(kbState, 0, kbPrevState, 0, kbState.length);

            // Actually, this runs the entire OS message pump.
            window.display();
            if (!window.isVisible()) {
                shouldExit = true;
                break;
            }

            // Game logic.
            if (kbState[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }
            int speed = 5;
            
	            if (kbState[KeyEvent.VK_LEFT] || kbState[KeyEvent.VK_A] && inBounds(spritePos[0]-speed, spritePos[1], window.getWidth(), window.getHeight())) {//left
	                 spritePos[0] -= speed;
	                 spriteTex = 2;
	            }
	
	            if (kbState[KeyEvent.VK_RIGHT]|| kbState[KeyEvent.VK_D] && inBounds(spritePos[0]+speed, spritePos[1], window.getWidth(), window.getHeight())) {//right
	            	  spritePos[0] += speed;
	            	  spriteTex = 1;
	            }
	
	            if (kbState[KeyEvent.VK_UP]|| kbState[KeyEvent.VK_W] && inBounds(spritePos[0], spritePos[1]-speed, window.getWidth(), window.getHeight())) {//up
	                spritePos[1] -= speed;
	            }
	
	            if (kbState[KeyEvent.VK_DOWN]|| kbState[KeyEvent.VK_S] && inBounds(spritePos[0], spritePos[1]+speed, window.getWidth(), window.getHeight())) {//down
	                spritePos[1] += speed;
	            }
           
            gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

            glDrawSprite(gl, spriteTex, spritePos[0], spritePos[1], spriteSize[0], spriteSize[1]);

            // Present to the player.
            //window.swapBuffers();
        }
        System.exit(0);
    }

    // Load a file into an OpenGL texture and return that texture.
    public static int glTexImageTGAFile(GL2 gl, String filename, int[] out_size) {
        final int BPP = 4;

        DataInputStream file = null;
        // Open the file.
		file = new DataInputStream(ResourceLoader.load(filename));

        try {
            // Skip first two bytes of data we don't need.
            file.skipBytes(2);

            // Read in the image type.  For our purposes the image type
            // should be either a 2 or a 3.
            int imageTypeCode = file.readByte();
            if (imageTypeCode != 2 && imageTypeCode != 3) {
                file.close();
                System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
                return 0;
            }

            // Skip 9 bytes of data we don't need.
            file.skipBytes(9);

            int imageWidth = Short.reverseBytes(file.readShort());
            int imageHeight = Short.reverseBytes(file.readShort());
            int bitCount = file.readByte();
            file.skipBytes(1);

            // Allocate space for the image data and read it in.
            byte[] bytes = new byte[imageWidth * imageHeight * BPP];

            // Read in data.
            if (bitCount == 32) {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = file.readByte();
                }
            } else {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = -1;
                }
            }

            file.close();

            // Load into OpenGL
            int[] texArray = new int[1];
            gl.glGenTextures(1, texArray, 0);
            int tex = texArray[0];
            gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
            gl.glTexImage2D(
                    GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0,
                    GL2.GL_BGRA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

            out_size[0] = imageWidth;
            out_size[1] = imageHeight;
            return tex;
        }
        catch (IOException ex) {
            System.err.format("File: %s -- Unexpected end of file.", filename);
            return 0;
        }
    }

    public static void glDrawSprite(GL2 gl, int tex, int x, int y, int w, int h) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
        gl.glBegin(GL2.GL_QUADS);
        {
            gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
            gl.glTexCoord2f(0, 1);
            gl.glVertex2i(x, y);
            gl.glTexCoord2f(1, 1);
            gl.glVertex2i(x + w, y);
            gl.glTexCoord2f(1, 0);
            gl.glVertex2i(x + w, y + h);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2i(x, y + h);
        }
        gl.glEnd();
    }
}
