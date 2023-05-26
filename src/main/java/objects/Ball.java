package objects;

import cena.Renderer;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Ball {
    private float x;
    private float y;
    private float radius;
    private int numSegments;
    private float velocityX = 0.015f;
    private float velocityY = 0.025f;
    private float translateX = 0.0f;
    private float translateY = 0.0f;
    private boolean collision = false;
    private boolean lose = false;
    private int points = 0;
    private int lifes = 5;
    TextRenderer textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.PLAIN, 38));
    private float bottomPosition;
    private float topPosition;
    private float leftPosistion;
    private float rightPosition;

    private boolean secondFase = false;

    public Ball(float x, float y, float radius, int numSegments) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.numSegments = numSegments;
    }

    public float getTranslateX(){
        return this.translateX;
    }
    public float getTranslateY() {
        return this.translateY;
    }
    public float getRadius() {
        return radius;
    }
    public int getLifes(){
        return this.lifes;
    }
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    public void setTranslateY(float translateY){
        this.translateY = translateY;
    }
    public void setSecondFase(boolean secondFase){
        this.secondFase = secondFase;
    }

    public String draw(GL2 gl){
        drawText(gl, 1840, 1020, Color.ORANGE, String.valueOf(this.points));

        if (!lose) {
            float theta = (float) (2 * Math.PI / numSegments);

            updatePosition();

            gl.glTranslatef(translateX, translateY, 0.0f);
            gl.glColor3f(0.5f,0.5f,0.5f);
            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glVertex2f(x, y); // Center of the circle
            for (int i = 0; i <= numSegments; ++i) {
                float x1 = (float) (x + radius * Math.cos(i * theta));
                float y1 = (float) (y + radius * Math.sin(i * theta));
                gl.glVertex2f(x1, y1);
            }
            gl.glEnd();
            gl.glLoadIdentity();


            if (this.points == 200)
                return "win";

        } else {
            if (this.lifes > 0 && !secondFase){
                this.lose = false;
                this.translateX = 0.0f;
                this.translateY = 0.0f;
                this.velocityY *= -1;
                this.velocityX = getRandomVelocity();
            } else if (this.lifes > 0 && secondFase) {
                this.lose = false;
                this.translateX = 0.0f;
                this.translateY = 0.9f;
                this.velocityX = getRandomVelocity();
            }

            if (this.lifes == 0){
                return "lose";
            }

        }
        return "";
    }

    public void print(GL2 gl){
        drawText(gl, 1840, 1020, Color.ORANGE, String.valueOf(this.points));
        float theta = (float) (2 * Math.PI / numSegments);

        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(translateX, translateY); // Center of the circle
        for (int i = 0; i <= numSegments; ++i) {
            float x1 = (float) (translateX + radius * Math.cos(i * theta));
            float y1 = (float) (translateY + radius * Math.sin(i * theta));
            gl.glVertex2f(x1, y1);
        }
        gl.glEnd();
        gl.glLoadIdentity();
    }

    public void drawBallObject(GL2 gl){
        drawText(gl, 1840, 1020, Color.ORANGE, String.valueOf(this.points));
        float theta = (float) (2 * Math.PI / numSegments);

        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(x, y); // Center of the circle
        for (int i = 0; i <= numSegments; ++i) {
            float x1 = (float) (x + radius * Math.cos(i * theta));
            float y1 = (float) (y + radius * Math.sin(i * theta));
            gl.glVertex2f(x1, y1);
        }
        gl.glEnd();
        gl.glLoadIdentity();
    }

    public void updatePosition(){
        this.translateX += velocityX;
        this.translateY += velocityY;

        if (this.translateX > 0.9f) {
            velocityX = -Math.abs(velocityX);
        } else  if (this.translateX < -0.9f){
            velocityX = Math.abs(velocityX);
        }

        if (this.translateY > 0.9f) {
            velocityY = -Math.abs(velocityY);
            velocityX = getRandomVelocity();
        } else if (this.translateY < -0.9f && !collision){
            this.lifes -= 1;
            this.lose = true;
        }
    }

    private float getRandomVelocity() {
        return (float) (Math.random() * 0.02 - 0.01);
    }

    public void collision(Rectangle rectangle) {
        calculatePositions();
        float rectTop = rectangle.getY2();
        float[] range = rectangle.calculateRange();

        if (this.bottomPosition <= rectTop && this.leftPosistion >= range[0] && this.rightPosition <= range[1]){
            velocityY = Math.abs(velocityY);
            velocityX = getRandomVelocity();
            this.collision = true;
            this.points += 20;
        } else {
            this.collision = false;
        }
    }

    public void ballCollision(Ball ball2){
        float distanceX = ball2.getTranslateX() - this.translateX;
        float distanceY = ball2.getTranslateY() - this.translateY;
        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distance < this.radius + ball2.getRadius()){
            if (this.velocityY < 0){
                velocityY = Math.abs(velocityY);
                velocityX = getRandomVelocity();
            } else if (this.velocityY > 0) {
                velocityY = -Math.abs(velocityY);
                velocityX = getRandomVelocity();
            }
        }
    }

    private void drawText(GL2 gl, int x, int y, Color color, String text) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(color);
        textRenderer.draw(text, x, y);
        textRenderer.endRendering();
    }

    public void reset(){
        this.lose = false;
        this.translateX = 0.0f;
        this.translateY = 0.0f;
        this.velocityY *= -1;
        this.velocityX = getRandomVelocity();
        this.lifes = 5;
        this.points = 0;
    }

    public void calculatePositions(){
        this.bottomPosition = this.translateY - this.radius;
        this.topPosition = this.translateY + this.radius;
        this.leftPosistion = this.translateX - this.radius;
        this.rightPosition = this.translateX + this.radius;
    }
}
