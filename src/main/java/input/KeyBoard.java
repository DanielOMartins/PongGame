package input;
import cena.Pong;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import objects.Rectangle;

/**
 *
 * @author Kakugawa
 */
public class KeyBoard implements KeyListener {
    private Pong pong;
    private Rectangle rectangle;
    private float x = 0.0f;
    private float velocity = 0.05f;

    public KeyBoard(Pong pong, Rectangle rectangle){
        this.pong = pong;
        this.rectangle = rectangle;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (pong.getResult().equals("lose"))
            this.x = 0.0f;

        short key = e.getKeyCode();
        boolean onPause = pong.getActiveScene().equals("pause");

        if (key == KeyEvent.VK_COMMA){
            pong.setActiveScene("pong2");
        }

        String borderLimit = verifyBorderLimit(rectangle.calculateRange(), x);
        if (key == KeyEvent.VK_LEFT && !borderLimit.equals("left") && !onPause ) {
            x -= velocity;
            rectangle.updatePosition(x);
        }
        if (key == KeyEvent.VK_RIGHT && !borderLimit.equals("right") && !onPause) {
            x += velocity;
            rectangle.updatePosition(x);
        }
        if (key == KeyEvent.VK_SPACE && (!pong.getActiveScene().equals("pong") || !pong.getActiveScene().equals("pong2"))){
            pong.setActiveScene("pong");
        }
        if(key == KeyEvent.VK_ESCAPE){
            if (pong.getActiveScene().equals("menu"))
                System.exit(0);

            pong.resetPong();
            this.x = 0.0f;
            pong.setActiveScene("menu");
        }
        if (key == KeyEvent.VK_TAB){
            pong.setActiveScene("pause");
        }

    }

    private String verifyBorderLimit(float[] rectRange, float x) {
        if (rectRange[0] <= -1.0f){
            return "left";
        }

        if (rectRange[1] >= 1.0f){
            return "right";
        }

        return "";
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
