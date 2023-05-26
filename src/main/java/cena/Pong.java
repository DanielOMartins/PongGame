package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import objects.Ball;
import objects.Life;
import objects.Rectangle;
import objects.Trapeze;

import java.awt.*;

public class Pong implements GLEventListener{
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private String activeScene = "menu";
    private String previousScene = "";
    private String result = "";
    private Rectangle startRectang = new Rectangle(0.0f, 0.0f, 0.3f, 0.7f);
    private Ball ballStructure = new Ball(0.0f, 0.0f, 0.2f, 100);
    private TextRenderer textRenderer;
    GL2 gl;
    GLU glu;
    Rectangle rectangle;
    Ball ball;
    Life life;

    public Pong(Rectangle rectangle, Ball ball){
        this.rectangle = rectangle;
        this.ball = ball;
        this.life = new Life();
    }

    //TODO: iniciar fase 2, criar cena de regras, mudar objetos para 3D e iluminar

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.PLAIN, 38));
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0.3f, 0.3f, 0.6f, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena
        *
        */
        if (activeScene.equals("menu")){
            renderMenu(drawable);
        } else if (activeScene.equals("pong")) {
            renderPong();
        }else if (activeScene.equals("pong2")) {
            renderPong2();
        } else if (activeScene.equals("win")){
            renderPong2();
        } else if (activeScene.equals("pause")) {
            renderPause();
        }


        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    private void renderMenu(GLAutoDrawable drawable) {
        drawStartButton();
        drawButtons();
    }

    public void renderPong() {
        life.draw(gl, ball.getLifes());
        ball.collision(rectangle);
        this.result = ball.draw(gl);
        rectangle.draw(gl);

        if (result.equals("lose")){
            resetPong();
            setActiveScene("menu");
        } else if (result.equals("win")){
            setActiveScene("pong2");
            ball.setVelocityY(0.025f);
            ball.setSecondFase(true);
        }
    }
    private void renderPong2() {
        life.draw(gl, ball.getLifes());
        ball.collision(rectangle);
        ball.ballCollision(ballStructure);
        this.result = ball.draw(gl);
        ballStructure.drawBallObject(gl);
        rectangle.draw(gl);

        if (result.equals("lose")){
            resetPong();
            setActiveScene("menu");
        }
    }

    public void renderPause(){
        if (getPreviousScene().equals("pong")){
            life.draw(gl, ball.getLifes());
            ball.print(gl);
            rectangle.print(gl);
        } else if (getPreviousScene().equals("pong2")) {
            life.draw(gl, ball.getLifes());
            ball.print(gl);
            rectangle.print(gl);
            ballStructure.drawBallObject(gl);
        }
        drawText(gl, 910, 530, Color.BLACK, "PAUSE");
    }

    public void resetPong(){
        life.reset();
        ball.reset();
        rectangle.reset();
    }

    private void drawButtons() {
        drawText(gl, 0, 120, Color.ORANGE, "To Start press Space");
        drawText(gl, 0, 70, Color.ORANGE, "To Stop press ESC");
        drawText(gl, 0, 20, Color.ORANGE, "To Pause press Tab");
    }

    private void drawStartButton() {
        startRectang.draw(gl);
        drawText(gl, 910, 530, Color.blue, "Start");
    }

    private void drawText(GL2 gl, int x, int y, Color color, String text) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(color);
        textRenderer.draw(text, x, y);
        textRenderer.endRendering();
    }

    public Rectangle getStartRectang(){
        return this.startRectang;
    }
    public String getActiveScene(){
        return this.activeScene;
    }
    public void setActiveScene(String sceneName){
        this.previousScene = getActiveScene();
        this.activeScene = sceneName;
    }
    public String getPreviousScene(){
        return this.previousScene;
    }
    public String getResult(){
        return this.result;
    }

}

