package input;

import cena.Pong;
import cena.Renderer;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import objects.Rectangle;

public class Mouse implements MouseListener {
    private Pong pong;
    private Rectangle rectangle;

    public Mouse(Pong pong){
        this.pong = pong;
        this.rectangle = pong.getStartRectang();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (clickedStartButton(e, rectangle)){
            pong.setActiveScene("pong");
        }

    }

    private boolean clickedStartButton(MouseEvent e, Rectangle rectangle) {
        float tx = (2.0f * (float)e.getX() / Renderer.screenWidth ) - 1.0f;
        float ty = 1.0f - (2.0f * (float)e.getY() / Renderer.screenHeight);

        float left = rectangle.getX1();
        float right = rectangle.getX2();
        float bottom = rectangle.getY1();
        float top = rectangle.getY2();

        return tx >= left && tx <= right && ty <= top && ty >= bottom && pong.getActiveScene().equals("menu");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {

    }
}
