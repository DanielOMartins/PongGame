package objects;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;

public class Rectangle {
    private float x1;
    private float x2;
    private float y1;
    private float y2;
    private float height;
    private float width;
    private float translateX = 0.0f;
    private float translateY = 0.0f;

    private float left = x1;
    private float right = x2;

    public Rectangle( float x, float y, float height, float width){
        this.x1 = x - width / 2;
        this.x2 = x + width / 2;
        this.y1 = y - height / 2;
        this.y2 = y + height / 2;
        this.height = height;
        this.width = width;

    }

    public void draw(GL2 gl){
        gl.glTranslatef(translateX, translateY, 0.0f);
        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(this.x1, this.y1);
            gl.glVertex2f(this.x2, this.y1);
            gl.glVertex2f(this.x2, this.y2);
            gl.glVertex2f(this.x1, this.y2);
        gl.glEnd();
    }

    public void print(GL2 gl){


        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(this.left, this.y1);
            gl.glVertex2f(this.right, this.y1);
            gl.glVertex2f(this.right, this.y2);
            gl.glVertex2f(this.left, this.y2);
        gl.glEnd();
    }

    public void updatePosition(float x){
        this.translateX = x;
    }

    public float getTranslateX(){
        return this.translateX;
    }

    public float[] calculateRange(){
        this.left = this.translateX - this.width / 2;
        this.right = this.translateX + this.width / 2;

        return new float[]{left, right};
    }

    public void reset(){
        this.translateX = 0.0f;
        this.translateY = 0.0f;
    }

    public float getY1(){
        return this.y1;
    }
    public float getY2(){
        return this.y2;
    }
    public float getX1() {
        return x1;
    }
    public float getX2() {
        return x2;
    }
}
