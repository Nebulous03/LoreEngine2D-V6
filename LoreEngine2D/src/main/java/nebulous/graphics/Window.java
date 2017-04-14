package nebulous.graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
	
	public static enum DisplayMode {
		WINDOWED,
		WINDOWED_FULLSCREEN,
		FULLSCREEN
	}
	
	private static boolean legacyMode = false;

	private String WINDOW_TITLE    = "NebulousGameEngine V6.0";
	private int    WINDOW_WIDTH    = 640;
	private int    WINDOW_HEIGHT   = 480;
	private long   WINDOW  		   = 0;
	
	private boolean WINDOW_RESIZED = false; 
	private boolean VSYNC  = false;
	
	private DisplayMode MODE = DisplayMode.WINDOWED;
	
	public Window() {
		this("NebulousGameEngine V6.0", 640, 480);
	}
	
	public Window(String title, int width, int height) {
		
		this.WINDOW_TITLE = title;
		this.WINDOW_WIDTH = width;
		this.WINDOW_HEIGHT = height;
		
	}
	
	public Window createWindow() {
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		GLFWErrorCallback errorCallback = new GLFWErrorCallback() {
			
			public void invoke(int error, long description) {
				System.out.println(getDescription(description));
				new Exception("Internal OpenGL error occurred...").printStackTrace();
			}
		};
		
		glfwSetErrorCallback(errorCallback);
		
		if (!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		if(MODE == DisplayMode.FULLSCREEN) {
			WINDOW = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, glfwGetPrimaryMonitor(), 0);
		} else if(MODE == DisplayMode.WINDOWED_FULLSCREEN) {
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
			public void invoke(long window, int width, int height) {
				WINDOW_WIDTH = width;
				WINDOW_HEIGHT = height;
				WINDOW_RESIZED = true;
			}
		}; 
		
		glfwSetWindowSizeCallback(WINDOW, windowSizeCallback);
		
		GLFWWindowCloseCallback windowCloseCallback = new GLFWWindowCloseCallback() {

			public void invoke(long window) {	// TODO: Add more support for this.
				glfwDestroyWindow(window);
				glfwTerminate();
				System.exit(1);
			}

		}; 
		
		glfwSetWindowCloseCallback(WINDOW, windowCloseCallback);

		GL.createCapabilities();
		
		int version = Integer.parseInt(glGetString(GL_VERSION).split(" ")[0].substring(0, 1));
		
		if(version < 3)
			legacyMode = true;
		
		if(legacyMode) {
			glMatrixMode(GL11.GL_PROJECTION) ;
			glLoadIdentity();
			glOrtho(0, 800, 0, 600, 1, -1) ; 
			glMatrixMode(GL11.GL_MODELVIEW);
			glLoadIdentity();
			glFlush();
		}
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glEnable(GL_CULL_FACE);
		
		glfwSwapInterval(VSYNC ? 1 : 0);
		
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
	
	public Window setDisplayMode(DisplayMode mode){
		this.MODE = mode;
		return this;
	}
	
	public DisplayMode getDisplayMode() {
		return MODE;
	}
	
	public Window setWindowTitle(String title) {
		this.WINDOW_TITLE = title;
		glfwSetWindowTitle(WINDOW, title);
		return this;
	}
 	
	public String getWindowTitle() {
		return WINDOW_TITLE;
	}
	
	public long getWindow() {
		return WINDOW;
	}
	
	public Window setSize(int width, int height) {
		this.WINDOW_WIDTH = width;
		this.WINDOW_HEIGHT = height;
		this.WINDOW_RESIZED = true;
		return this;
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
	
	public void vSync(boolean vsync) {
		glfwSwapInterval(vsync ? 1 : 0);
		this.VSYNC = vsync;
	}
	
	public Window vSync() {
		VSYNC = true;
		return this;
	}
	
	public boolean isVSyncEnabled() {
		return VSYNC;
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

	public boolean resized() {
		return WINDOW_RESIZED;
	}
	
	public static boolean isLegacy() {
		return legacyMode;
	}
	
	public void forceLegacyMode() {
		legacyMode = true;
	}
}
