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
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)
            listener.wReleased();
        if (code == KeyEvent.VK_S)
            listener.sReleased();
        if (code == KeyEvent.VK_A)
            listener.aReleased();
        if (code == KeyEvent.VK_D)
            listener.dReleased();
        if (code == KeyEvent.VK_Q)
            listener.qReleased();
        if (code == KeyEvent.VK_E)
            listener.eReleased();
        if (code == KeyEvent.VK_SPACE)
            listener.spaceReleased();
    }

    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)
            listener.wPressed();
        if (code == KeyEvent.VK_S)
            listener.sPressed();
        if (code == KeyEvent.VK_A)
            listener.aPressed();
        if (code == KeyEvent.VK_D)
            listener.dPressed();
        if (code == KeyEvent.VK_Q)
            listener.qPressed();
        if (code == KeyEvent.VK_E)
            listener.ePressed();
        if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
    }

    public void setInputListener(InputListener listener)
    {
        this.listener = listener;
    }
}
