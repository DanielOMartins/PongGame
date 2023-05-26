package objects;

import com.jogamp.opengl.GL2;

public class Trapeze {
    float vertex1X = -0.3f;
    float vertex1Y = -0.2f;
    float vertex2X = -0.1f;
    float vertex2Y = 0.2f;
    float vertex3X = 0.1f;
    float vertex3Y = 0.2f;
    float vertex4X = 0.3f;
    float vertex4Y = -0.2f;
    private float[] vertices = {
            vertex1X, vertex1Y, // Bottom-left 1
            vertex4X, vertex4Y,  // Bottom-right 4
            vertex3X, vertex3Y,   // Top-right 3
            vertex2X, vertex2Y   // Top-left 2
    };

    public Trapeze(){}

    public void draw(GL2 gl){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(0.5f,0.5f,0.5f);
        for (int i = 0; i < vertices.length; i += 2) {
            float x = vertices[i];
            float y = vertices[i + 1];
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glLoadIdentity();
    }

    public float topRange(){
        return Math.max(vertex1Y, Math.max(vertex2Y, Math.max(vertex3Y, vertex4Y)));
    }

    public float bottomRange(){
        return Math.min(vertex1Y, Math.min(vertex2Y, Math.min(vertex3Y, vertex4Y)));
    }

    public float leftRange(){
        return Math.min(vertex1X, Math.min(vertex2X, Math.min(vertex3X, vertex4X)));
    }

    public float rightRange(){
        return Math.max(vertex1X, Math.max(vertex2X, Math.max(vertex3X, vertex4X)));
    }

    public float topRightRange(){
        return vertex3X;
    }
    public float topLeftRange(){
        return vertex2X;
    }
}
