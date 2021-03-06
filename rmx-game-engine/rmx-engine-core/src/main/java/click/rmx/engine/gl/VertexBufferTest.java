package click.rmx.engine.gl;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;


import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;



public class VertexBufferTest {

	private static int vertexBufferID;
	private static int colourBufferID;
	private static int numberIndices;
	private static int indexBufferID;
	private static int maxIndex;
	private static int indexBufferSize;
	private static GLFWErrorCallback _errorCallback;
	private static long _window;
	private static int _height = 600;
	private static int _width = 600;
	private static KeyCallback _keyCallback;
	private static FloatBuffer vertexBufferData;
	private static IntBuffer indexBufferData;
	
	static float points[] = {
			   0.0f,  0.5f,  0.0f,
			   0.5f, -0.5f,  0.0f,
			  -0.5f, -0.5f,  0.0f
			};
	
	static float colours[] = {
			  1.0f, 0.0f,  0.0f,
			  0.0f, 1.0f,  0.0f,
			  0.0f, 0.0f,  1.0f
			};
	
	public static int createVBOID() {
	    IntBuffer buffer = BufferUtils.createIntBuffer(1);
//	    numberIndices = points.length;
	    GL15.glGenBuffers(buffer);
	    return buffer.get(0);
	    //Or alternatively you can simply use the convenience method:
//	    return GL15.glGenBuffers(); //Which can only supply you with a single id.
	}
	
	
	
	public static void vertexBufferData(int id, FloatBuffer buffer) { //Not restricted to FloatBuffer
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id); //Bind buffer (also specifies type of buffer)
	    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Send up the data and specify usage hint.
	}
	
	public static void indexBufferData(int id, IntBuffer buffer) { //Not restricted to IntBuffer
	    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
	    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	

	public static void render() {
	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferID);
	    GL11.glVertexPointer(3, GL11.GL_FLOAT, points.length, points.length / 3);
	     
	    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colourBufferID);
	    GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
	     
	    //If you are not using IBOs:
	    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, numberIndices);
	 
	    //If you are using IBOs:
	    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferID);
	    GL11.glDrawElements(GL11.GL_TRIANGLES, numberIndices, GL11.GL_UNSIGNED_INT, 0);
	 
	    //The alternate glDrawElements.    
	    GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, maxIndex, numberIndices,
	                    GL11.GL_UNSIGNED_INT, 0);
	}
	
	
	//ARB
	public static int createARBVBOID() {
		  if (GLContext.createFromCurrent().getCapabilities().GL_ARB_vertex_buffer_object) {
		    IntBuffer buffer = BufferUtils.createIntBuffer(1);
		    ARBVertexBufferObject.glGenBuffersARB(buffer);
		    return buffer.get(0);
		  }
		  return 0;
		}
	public static void bufferData(int id, FloatBuffer buffer) {
		  if (GLContext.createFromCurrent().getCapabilities().GL_ARB_vertex_buffer_object) {
		    ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, id);
		    ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		  }
		}
	
	
	
	
	
	//ARB Extension format.
	public static void renderARB() {
	  GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	  ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferID);
	  GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
	  
	  GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
	  ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, colourBufferID);
	  GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
	  
	  ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indexBufferID);
	  GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, maxIndex, indexBufferSize,
	                    GL11.GL_UNSIGNED_INT, 0);
	}
	
	
	public static void initGL() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(_errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

 
        // Create the window
        _window = glfwCreateWindow(_width, _height, "Hello World!", NULL, NULL);
        if ( _window == MemoryUtil.NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(_window, _keyCallback = KeyCallback.getInstance());
 
        glfwSetCursorPosCallback(_window, CursorCallback.getInstance());
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            _window,
            (GLFWvidmode.width(vidmode) - _width) / 2,
            (GLFWvidmode.height(vidmode) - _height) / 2
        );

        GLFW.glfwSetWindowSizeCallback(_window, windowSizeCallback = new GLFWWindowSizeCallback() {
        	
			@Override
			public void invoke(long arg0, int width, int height) {
//				GameController.getInstance().getView().setSize(width,height);
				
			}
        	
        });
        // Make the OpenGL context current
        glfwMakeContextCurrent(_window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(_window);
       

    }
    private static GLFWWindowSizeCallback windowSizeCallback;
 

    static {
    	numberIndices = points.length;
    	vertexBufferData = BufferUtils.createFloatBuffer(9);
    	indexBufferData = BufferUtils.createIntBuffer(3);
    }
    
	public static void enterGameLoop() {
//    	glfwGenuffers(1, frameBuffer);
   
        GLContext.createFromCurrent();
  
//        SharedLibraryLoader.load();
		
//		vertexBufferID = createVBOID();

//		vertexBufferData(vertexBufferID, vertexBufferData);
//		indexBufferData(vertexBufferID, indexBufferData);
		
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
//        pointOfView().camera().perspective(this);
//   
        
//        this.pointOfView().camera().setAspect(_width, _height);
        while ( glfwWindowShouldClose(_window) == GL_FALSE ) {
//        	Scene scene = Scene.getCurrent();

            
           
            
//        	scene.updateSceneLogic();
        	
        	glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
//            camera.perspective(this);
            
             
        	
            glClearColor(0.3f, 0.3f, 0.3f, 0.3f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glMatrixMode(GL_MODELVIEW);
            glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
            glLoadIdentity();  
            render();
//            camera.look();

        	
        	glfwSwapBuffers(_window);

        	glMatrixMode(GL_PROJECTION);
        	 // swap the color buffers
        	
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
//            this.didCauseEvent(END_OF_GAMELOOP);
        }
    }

	public static void main(String[] args) {
		try {
		initGL();
		enterGameLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
