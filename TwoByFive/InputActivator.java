import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 * Write a description of class InputListener here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class InputActivator implements KeyListener
{
    private InputListener listener;

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)
            listener.wPressed();
        else if (code == KeyEvent.VK_S)
            listener.sPressed();
        else if (code == KeyEvent.VK_A)
            listener.aPressed();
        else if (code == KeyEvent.VK_D)
            listener.dPressed();
        else if (code == KeyEvent.VK_Q)
            listener.qPressed();
        else if (code == KeyEvent.VK_E)
            listener.ePressed();
    }

    public void setInputListener(InputListener listener)
    {
        this.listener = listener;
    }
}
