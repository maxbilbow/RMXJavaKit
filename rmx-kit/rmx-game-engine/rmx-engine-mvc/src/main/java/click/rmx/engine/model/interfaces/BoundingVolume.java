package click.rmx.engine.model.interfaces;

/**
 * Created by Max on 28/10/2015.
 */
public interface BoundingVolume {

    GameObject getGameObject();

    float [] getBoundingBoxMin();

    float [] getBoundingBoxMax();

    /**
     * centre: x, y, z, radius: w
     * @return float[] with lenght 4
     *
     */
    float [] getBoundingSphere();

    /**
     * Called whenerver scale or geometry is changed
     */
    default void updateBounds()
    {

        final String shape = getGameObject().getGeometryName();
        if (shape == null)
            return;
        final int x = 0, y = 1, z = 2, radius = 3;
        Geometry geometry = Geometry.GeometryPool.getGeometryForKey(shape);
        float [] scale = getGameObject().getScale();
        float width = geometry.getWidth() * scale[x];
        float height = geometry.getHeight() * scale[y];
        float length = geometry.getDepth() * scale[z];

        float[] boxMax = getBoundingBoxMax();
        float[] boxMin = getBoundingBoxMin();
        float[] sphere = getBoundingSphere();

        boxMax[x] = width / 2;
        boxMax[y] = height / 2;
        boxMax[z] = length / 2;

        boxMin[x] = width / -2;
        boxMin[y] = height / -2;
        boxMin[z] = length / -2;

        sphere[radius] = geometry.getRadius();

    }



//    public Vector3 getCollisionNormal(BoundingBox other) {
//        Vector3 a = this.transform.position();
//        Vector3 b = other.transform.position();
//        Vector3 normal = new Vector3();
//
//        float xMinA = this.xMin() + a.x;
//        float xMaxA = this.xMax() + a.x;
//        float yMinA = this.yMin() + a.y;
//        float yMaxA = this.yMax() + a.y;
//        float zMinA = this.zMin() + a.z;
//        float zMaxA = this.zMax() + a.z;
//
//        float xMinB = other.xMin() + b.x;
//        float xMaxB = other.xMax() + b.x;
//        float yMinB = other.yMin() + b.y;
//        float yMaxB = other.yMax() + b.y;
//        float zMinB = other.zMin() + b.z;
//        float zMaxB = other.zMax() + b.z;
//
//        //find the x value
////		if (xMinA < xMaxB && xMinA > xMinB)
//
//
//        return normal;
//    }
}
