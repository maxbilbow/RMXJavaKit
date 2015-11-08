package click.rmx.engine.components;

import static org.lwjgl.opengl.GL11.*;

import click.rmx.engine.math.BufferedVector3;
import click.rmx.engine.math.BufferedVector4;

public class LightSource extends ANodeComponent {
	
	public static final LightSource defaultLightSource = new LightSource();

	BufferedVector4 specular = new BufferedVector4(0.9f,0.9f,0.9f,1);
	BufferedVector4 ambient = new BufferedVector4(0.1f,0.1f,0.1f,0.1f);
	BufferedVector4 diffuse = new BufferedVector4(0.9f,0.9f,0.9f,0.3f);
	BufferedVector4 material = new BufferedVector4(0.9f,0.9f,0.9f,0.3f);
	BufferedVector3 position = new BufferedVector3();
	public void shine() {
		
		glShadeModel(GL_SMOOTH);
		glMaterialfv(GL_FRONT, GL_SPECULAR, material.getBuffer());				// sets specular material color
		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess
		

		position.set(this.transform().position());

		glLightfv(GL_LIGHT0, GL_POSITION, position.getBuffer());				// sets light position
		glLightfv(GL_LIGHT0, GL_SPECULAR, specular.getBuffer());				// sets specular light to white
		glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuse.getBuffer());					// sets diffuse light to white
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, ambient.getBuffer());		// global ambient light 
		
		glEnable(GL_LIGHTING);										// enables lighting
		glEnable(GL_LIGHT0);										// enables light0
		
		glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	}
}
