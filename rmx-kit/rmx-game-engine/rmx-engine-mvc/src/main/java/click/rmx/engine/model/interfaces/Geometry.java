package click.rmx.engine.model.interfaces;

import java.util.HashMap;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface Geometry {

    default Object getVertexShaderProgram()
    {
        return null; //TODO
    }

    default Object getFragmentShaderProgram()
    {
        return null; //TODO
    }

    float[] getVertexData();

    float[] getColorData();

    float[] getNormalData();

    short[] getIndexData();

    String getName();

    default float getWidth(){
        return 2;
    }

    default float getHeight() {
        return 2;
    }

    default float getDepth() {
        return 2;
    }

    default float getRadius()
    {
        return (getHeight() + getDepth() + getWidth()) / 6;
    }

    class GeometryPool {
        private static int count = 0;
        private static HashMap<String, Geometry> geometries;

        public static String registerGeometry(Geometry geometry)
        {
            if (geometry.getName() == null)
                throw new NullPointerException("Geometry must have a unique name for registration");
            geometries.put(geometry.getName(),geometry);
            return geometry.getName();
        }

        public static Geometry getGeometryForKey(String key) {
            return geometries.get(key);
        }
    }

    enum Primitive implements Geometry {
        Cube(  "Primitive::CUBE",
                Data.cubeVertexData,
                Data.cubeIndexData,
                null,
                Data.cubeNormalData
        ) {

        };

        final float[] vertices, color, normals;
        final short[] indices;

        private String name;
        Primitive(String name, float[] vertices, short[] indices, float[] normals, float[] color)
        {
            this.vertices = vertices;
            this.color = color;
            this.indices = indices;
            this.normals = normals;
            this.name = name;
            GeometryPool.registerGeometry(this);
        }
        @Override
        public float[] getVertexData() {
            return vertices;
        }

        @Override
        public float[] getColorData() {
            return color;
        }

        @Override
        public short[] getIndexData() {
            return indices;
        }
        @Override
        public float[] getNormalData() {
            return normals;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}

final class Data {
    protected static float [] cubeVertexData = {
            //Front
            -1.0f, -1.0f, 1.0f,1.0f,//        0.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 1.0f,1.0f,//         0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,//         0.0f, 0.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, 1.0f,//        0.0f, 0.0f, 1.0f,

            //back
            1.0f, 1.0f, -1.0f, 1.0f,//        0.0f, 0.0f,-1.0f,
            1.0f, -1.0f, -1.0f, 1.0f,//       0.0f, 0.0f,-1.0f,
            -1.0f, -1.0f, -1.0f, 1.0f,//      0.0f, 0.0f,-1.0f,
            -1.0f, 1.0f, -1.0f, 1.0f,//       0.0f, 0.0f,-1.0f,

            //bottom
            -1.0f, -1.0f, -1.0f,1.0f,//       0.0f,-1.0f, 0.0f,
            1.0f, -1.0f, -1.0f, 1.0f,//       0.0f,-1.0f, 0.0f,
            1.0f, -1.0f, 1.0f,  1.0f,//       0.0f,-1.0f, 0.0f,
            -1.0f, -1.0f, 1.0f, 1.0f,//       0.0f,-1.0f, 0.0f,

            //Top
            1.0f, 1.0f, 1.0f,  1.0f,//        0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, -1.0f,  1.0f,//       0.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, -1.0f, 1.0f,//       0.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, 1.0f, 1.0f,//        0.0f, 1.0f, 0.0f,

            //right
            1.0f, -1.0f,-1.0f,1.0f,//         1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, -1.0f,1.0f,//         1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,//         1.0f, 0.0f, 0.0f,
            1.0f, -1.0f, 1.0f,1.0f,//         1.0f, 0.0f, 0.0f,

            //left
            -1.0f, 1.0f, 1.0f,  1.0f,//      -1.0f, 0.0f, 0.0f,
            -1.0f, 1.0f, -1.0f, 1.0f,//      -1.0f, 0.0f, 0.0f,
            -1.0f, -1.0f, -1.0f, 1.0f,//     -1.0f, 0.0f, 0.0f,
            -1.0f, -1.0f, 1.0f, 1.0f//      -1.0f, 0.0f, 0.0
    };



    static final float[] cubeNormalData = {
            //Front
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            //back
            0.0f, 0.0f,-1.0f,
            0.0f, 0.0f,-1.0f,
            0.0f, 0.0f,-1.0f,
            0.0f, 0.0f,-1.0f,

            //bottom
            0.0f,-1.0f, 0.0f,
            0.0f,-1.0f, 0.0f,
            0.0f,-1.0f, 0.0f,
            0.0f,-1.0f, 0.0f,

            //Top
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            //right
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            //left
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f
    };


    //float cubeColors[] = [
    //              [0.8, 0.8, 0.8, 1.0], // Front face: white
    //              [1.0f, 0.0f, 0.0f, 1.0], // Back face: red
    //              [0.0f, 1.0f, 0.0f, 1.0], // Top face: green
    //              [0.0f, 0.0f, 1.0f, 1.0], // Bottom face: blue
    //              [1.0f, 1.0f, 0.0f, 1.0], // Right face: yellow
    //              [1.0f, 0.0f, 1.0f, 1.0]     // Left face: purple
    //              ];
    //
    //var generatedColors = [];
    //
    //for (j = 0; j < 6; j++) {
    //    var c = colors[j];
    //
    //    for (var i = 0; i < 4; i++) {
    //        generatedColors = generatedColors.concat(c);
    //    }
    //}


    // This array defines each face as two triangles, using the
    // indices into the vertex array to specify each triangle's
    // position.

    static final short cubeIndexData[] = {
            0,1,2,0,2,3, //Front
            4,5,6,4,6,7, //Back
            8,9,10,8,10,11, //top
            12,13,14,12,14,15, //bottom
            16,17,18,16,18,19, //right
            20,21,22,20,22,23 //left   // left
    };



    static final float triandleVertexData[] = {
            -1.0f, -1.0f, 0.0f, 1.0f,
            -1.0f,  1.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f, 1.0f,

            1.0f, -1.0f, 0.0f, 1.0f,
            -1.0f,  1.0f, 0.0f, 1.0f,
            1.0f,  1.0f, 0.0f, 1.0f,

            -0.0f, 0.25f, 0.0f, 1.0f,
            -0.25f, -0.25f, 0.0f, 1.0f,
            0.25f, -0.25f, 0.0f, 1.0f
    };



    //let txtCoords: [Float] = [
    //                          //Front
    //                          -1.0f, -1.0f,
    //                          1.0f, -1.0f,
    //                          1.0f, 1.0f,
    //                          -1.0f, 1.0f,
    //
    //                          //back
    //                          1.0f, 1.0f,
    //                          1.0f, -1.0f,
    //                          -1.0f, -1.0f,
    //                          -1.0f, 1.0f,
    //
    //                          //bottom
    //                          -1.0f, -1.0f,
    //                          1.0f, -1.0f,
    //                          1.0f, -1.0f,
    //                          -1.0f, -1.0f,
    //
    //                          //Top
    //                          1.0f, 1.0f,
    //                          1.0f, 1.0f,
    //                          -1.0f, 1.0f,
    //                          -1.0f, 1.0f,
    //
    //                          //right
    //                          1.0f, -1.0f,
    //                          1.0f, 1.0f,
    //                          1.0f, 1.0f,
    //                          1.0f, -1.0f,
    //
    //                          //left
    //                          -1.0f, 1.0f,
    //                          -1.0f, 1.0f,
    //                          -1.0f, -1.0f,
    //                          -1.0f, -1.0f,
    //                          ]
}


