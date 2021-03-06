package click.rmx.engine.gl;

import click.rmx.engine.GameController;
import click.rmx.engine.components.Nodes;
import click.rmx.math.Vector3;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorCallback extends GLFWCursorPosCallback {
//	void center();
//	void MouseButton(int button, int state, int x, int y);
//	void MouseMotion(int x, int y);
//	void mouseFree(int x, int y);
	
	private static CursorCallback singleton = new CursorCallback();
	    private CursorCallback() {    }
	    public static CursorCallback getInstance() {
	    	return singleton;
	    }
	    

	private double xpos ,ypos;
	private boolean restart = true;
	private boolean cusorLocked = false;
	@Override
	public void invoke(long window, double xpos, double ypos) {
		if (!cusorLocked) 
			return;
		if (restart) {
			this.xpos = xpos;
			this.ypos = ypos;
			restart = false;
			return;
		} else {
			double dx = xpos - this.xpos;
			double dy = ypos - this.ypos;
			dx *= 0.05 * 0.2; dy *= 0.01 * 0.2;
			this.xpos = xpos;
			this.ypos = ypos;
			Nodes.getCurrent().physicsBody().applyTorque((float)dx, "yaw", Vector3.Zero);
			GameController.getInstance().getView().pointOfView().broadcastMessage("lookUp",(float)dy);
//			Bugger.logAndPrint(dx + ", " + dy, false);
		}
		
		
	}
	public boolean isCursorLocked() {
		return cusorLocked;
	}
	public void lockCursor(boolean lock) {
		this.cusorLocked = lock;
		this.restart = true;
	}
}
