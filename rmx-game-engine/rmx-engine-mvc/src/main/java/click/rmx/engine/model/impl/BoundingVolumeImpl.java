package click.rmx.engine.model.impl;

import click.rmx.engine.model.interfaces.BoundingVolume;
import click.rmx.engine.model.interfaces.GameObject;
import javafx.beans.NamedArg;
import javafx.geometry.BoundingBox;

/**
 * Created by Max on 28/10/2015.
 */
public class BoundingVolumeImpl extends BoundingBox implements BoundingVolume {
    public BoundingVolumeImpl(@NamedArg("minX") double minX, @NamedArg("minY") double minY, @NamedArg("minZ") double minZ, @NamedArg("width") double width, @NamedArg("height") double height, @NamedArg("depth") double depth) {
        super(minX, minY, minZ, width, height, depth);
    }

    public BoundingVolumeImpl(@NamedArg("minX") double minX, @NamedArg("minY") double minY, @NamedArg("width") double width, @NamedArg("height") double height) {
        super(minX, minY, width, height);
    }

    @Override
    public GameObject getGameObject() {
        return null;
    }

    @Override
    public float[] getBoundingBoxMin() {
        return new float[0];
    }

    @Override
    public float[] getBoundingBoxMax() {
        return new float[0];
    }

    @Override
    public float[] getBoundingSphere() {
        return new float[0];
    }

//    float xMin() {
//        return - this.transform.getWidth() / 2;
//    }
//    float yMin() {
//        return - this.transform.getHeight() / 2;
//    }
//    float zMin() {
//        return - this.transform.getLength() / 2;
//    }
//    float xMax() {
//        return + this.transform.getWidth() / 2;
//    }
//    float yMax() {
//        return + this.transform.getHeight() / 2;
//    }
//    float zMax() {
//        return + this.transform.getLength() / 2;
//    }
//
//    @Override
//    public boolean intersects(CollisionBounds bounds) {
//        BoundingBox other = (BoundingBox) bounds;
//        Vector3 a = this.transform.position();
//        Vector3 b = other.transform.position();
//
//        if (this.xMin() + a.x > other.xMax() + b.x)
//            return false;
//        if (this.yMin() + a.y > other.yMax() + b.y)
//            return false;
//        if (this.zMin() + a.z > other.zMax() + b.z)
//            return false;
//        if (this.xMax() + a.x < other.xMin() + b.x)
//            return false;
//        if (this.yMax() + a.y < other.yMin() + b.y)
//            return false;
//        if (this.zMax() + a.z < other.zMin() + b.z)
//            return false;
//
//        return true;
//    }

}
