precision mediump float;

attribute vec4 aPosition;
attribute vec4 colors;
uniform float time;
uniform mat4 viewMatrix;
varying vec3 fragPosition;
varying vec4 fragColors;

uniform mat4 projectionMatrix;

#define PI (3.1459)
#define TWO_PI (3.1459*2.0)
void main()
{
   
    float r = time;//-0.8;
    float r2 = r * -0.5;
    
    mat4 ry = mat4(
        cos(r), 0.0, sin(r), -0.0,
        0.0, 1.0, 0.0, -0.0,
        -sin(r), 0.0, cos(r), -0.0,
        0.0, 0.0, 0.0,  1.0
    );
    
    mat4 rx = mat4(
                   1.0, 0.0, 0.0, -0.0,
                   0.0, cos(r2), -sin(r2), -0.0,
                   0.0, sin(r2), cos(r2), -0.0,
                   0.0, 0.0, 0.0,  1.0
                   );
    
    mat4 translation = mat4(
                           1.0, 0.0, 0.0, 0.0,
                            0.0, 1.0, 0.0, 0.0,
                           0.0, 0.0, 1.0, 0.0,
                            0.0, 0.0, -4.0,  1.0
                            );
//    gl_Position = aPosition;
    gl_Position =  projectionMatrix * viewMatrix * translation  * rx * ry * aPosition;// vec4(aPosition.x, aPosition.y, aPosition.z-0.5, 1.0);
//    vec4(progress * aPosition.x + (1.0 - progress) * aPosition.y,
//                       progress * aPosition.y + (1.0 - progress) * aPosition.x,
//                       0.0,1.0);
    fragPosition = gl_Position.xyz;
    fragColors = vec4(colors);
}