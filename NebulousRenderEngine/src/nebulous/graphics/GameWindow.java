package nebulous.graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameWindow {
	
	public static final int DISPLAY_MODE_WINDOWED 		     = 0; 
	public static final int DISPLAY_MODE_FULLSCREEN 		 = 1;
	public static final int DISPLAY_MODE_WINDOWED_FULLSCREEN = 2;

	private String WINDOW_TITLE    = "NebulousGameEngine V6.0";
	private int    WINDOW_WIDTH    = 640;
	private int    WINDOW_HEIGHT   = 480;
	private long   WINDOW  		   = 0;
	
	private boolean WINDOW_RESIZED = false; 
	private boolean VSYNC_ENABLED  = false;
	private int		DISPLAY_MODE   = 0;
	
	public GameWindow() {
		this("NebulousGameEngine V6.0", 640, 480);
	}
	
	public GameWindow(String title, int width, int height) {
		
		this.WINDOW_TITLE = title;
		this.WINDOW_WIDTH = width;
		this.WINDOW_HEIGHT = height;
		
	}
	
	public GameWindow createWindow() {
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		if(DISPLAY_MODE == DISPLAY_MODE_FULLSCREEN) {
			WINDOW = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, glfwGetPrimaryMonitor(), 0);
		} else if(DISPLAY_MODE == DISPLAY_MODE_WINDOWED_FULLSCREEN) {
			WINDOW = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, 0, 0);
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			glfwSetWindowPos(WINDOW,(vidmode.width() - WINDOW_WIDTH) / 2,(vidmode.height() - WINDOW_HEIGHT) / 2);
		} else {
			WINDOW = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, 0, 0);
			glfwSetWindowPos(WINDOW,(vidmode.width() - WINDOW_WIDTH) / 2,(vidmode.height() - WINDOW_HEIGHT) / 2);
		}
		
		if (WINDOW == 0)
			throw new RuntimeException("Failed to create the GLFW window");
		
		glfwMakeContextCurrent(WINDOW);
		
		glfwSwapInterval(0);	// VSync
		
		GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				WINDOW_WIDTH = width;
				WINDOW_HEIGHT = height;
				WINDOW_RESIZED = true;
			}
		}; 
		
		glfwSetWindowSizeCallback(WINDOW, windowSizeCallback);
		
		GLFWWindowCloseCallback windowCloseCallback = new GLFWWindowCloseCallback() {

			@Override
			public void invoke(long window) {	// TODO: Add more support for this.
				glfwDestroyWindow(window);
				glfwTerminate();
				System.exit(1);
			}

		}; 
		
		glfwSetWindowCloseCallback(WINDOW, windowCloseCallback);

		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		
		glClearColor(0.01f, 0.04f, 0.04f, 0.0f);
		
		return this;
	}
	
	public void init() {
		glfwShowWindow(WINDOW);
	}
	
	public void update() {
		
		if(WINDOW_RESIZED) {
			glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			WINDOW_RESIZED = false;
		}
		
	}
	
	public void render() {
		
		glfwSwapBuffers(WINDOW);
		glfwPollEvents();
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
	}
	
	public void setDisplayMode(int mode){
		this.DISPLAY_MODE = mode;
	}
	
	public int getDisplayMode() {
		return DISPLAY_MODE;
	}
	
	public void setWindowTitle(String title) {
		this.WINDOW_TITLE = title;
		glfwSetWindowTitle(WINDOW, title);
	}
 	
	public String getWindowTitle() {
		return WINDOW_TITLE;
	}
	
	public long getWindow() {
		return WINDOW;
	}
	
	public void setSize(int width, int height) {
		this.WINDOW_WIDTH = width;
		this.WINDOW_HEIGHT = height;
		this.WINDOW_RESIZED = true;
	}
	
	public void setWidth(int width) {
		this.WINDOW_WIDTH = width;
	}
	
	public int getWidth() {
		return WINDOW_WIDTH;
	}
	
	public void setHeight(int height) {
		this.WINDOW_HEIGHT = height;
	}
	
	public int getHeight() {
		return WINDOW_HEIGHT;
	}
	
	public void enableVSync(boolean vsync) {
		glfwSwapInterval(vsync ? 1 : 0);
		this.VSYNC_ENABLED = vsync;
	}
	
	public boolean isVSyncEnabled() {
		return VSYNC_ENABLED;
	}
	
	public void printGLStats(){
        System.out.println(
        " OPENGL: " + glGetString(GL_VERSION) + "\n" +
        " LWJGL: " + Version.getVersion() + "\n" + 
        " GRAPHICS: " + glGetString(GL_RENDERER) + "\n" +
        " VENDORS: " + glGetString(GL_VENDOR) + "\n" +
        " OPERATING SYSTEM: " + System.getProperty("os.name") + "\n" +
        " JAVA VERSION: " + System.getProperty("java.version") + "\n" +
        " CURRENT DIRECTORY: \n" +
        " " + System.getProperty("user.dir")
        );
    }
}
