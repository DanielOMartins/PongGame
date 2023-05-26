package objects;

import com.jogamp.opengl.GL2;

public class Life {
    private int lifes = 5;
    private int countVertices = 10;
    private float[] vertices = {
            -0.923f, 0.95f, // Top
            -0.875f, 0.9f, // Upper right
            -0.89f, 0.8f, // Lower right
            -0.96f, 0.8f, // Lower left
            -0.97f, 0.9f // Upper left
    };

    public Life(){}

    public void draw(GL2 gl, int lifes){
        if (lifes < this.lifes){
            countVertices -= 2;
        }

        gl.glColor3f(1.0f, 0.5f, 0.0f);
        gl.glLineWidth(3.0f);
        gl.glBegin(GL2.GL_LINES);
        for (int i = 0; i < countVertices; i += 2) {
            float x = vertices[i];
            float y = vertices[i + 1];
            float x2 = 0;
            float y2 = 0;

            if (i == 8){
                x2 = vertices[0];
                y2 = vertices[1];
            } else {
                x2 = vertices[i + 2];
                y2 = vertices[i + 3];
            }

            gl.glVertex2f(x, y);
            gl.glVertex2f(x2, y2);
        }
        gl.glEnd();
        gl.glLoadIdentity();
        gl.glFlush();

        this.lifes = lifes;
    }

    public void reset(){
        this.lifes = 5;
        this.countVertices = 10;
    }
}
