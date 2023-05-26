package cena;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import input.KeyBoard;
import input.Mouse;
import objects.Ball;
import objects.Rectangle;

public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 1920;  //1280
    public static int screenHeight = 1080; //960
    public static Renderer renderer;

    //Cria a janela de rendeziração do JOGL
    public static void init(){
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        //window.setFullscreen(true);

        Rectangle rectangle = new Rectangle(0.0f, -1f, 0.1f, 0.7f);
        Ball ball = new Ball(0.0f, 0.0f, 0.1f, 100);


        Pong pong = new Pong(rectangle, ball);
        KeyBoard keyBoard = new KeyBoard(pong, rectangle);
        Mouse mouse = new Mouse(pong);

        window.addGLEventListener(pong);
        window.addKeyListener(keyBoard);
        window.addMouseListener(mouse);
        
        //window.requestFocus();
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start(); //inicia o loop de animação
        
        //encerrar a aplicacao adequadamente
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });       
        
        //window.setFullscreen(true);        
        window.setVisible(true);
    }
  
    public static void main(String[] args) {
        init();
    }
}
