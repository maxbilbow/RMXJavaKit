package click.rmx.engine.model;

import click.rmx.math.Matrix4;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface GameObject {

    Matrix4 getLocalMatrix();

    Matrix4 getWorldMatrix();

    GameObject getParent();


    abstract class DefaultGameObject implements GameObject {

    }
}
