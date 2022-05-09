import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class gui extends JFrame implements KeyListener {
    final static int width = 800;
    final static int height = 600;
    final camera camera;
    final ArrayList<cube> cubes;
    private final static Color background = new Color(0, 0, 50);
    private final static Color foreground = new Color(238, 255, 98, 255);

    public gui(camera camera, ArrayList<cube> cubes) {
        this.camera = camera;
        this.cubes = cubes;
        setSize(width, height);
        setLayout(null);
        addKeyListener(this);
        setTitle("GK1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(background);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setClip(0, 0, width, height);
        g2D.setColor(background);
        g2D.fillRect(0, 0, width, height);
        g2D.setColor(foreground);
        for (cube cube : cubes) {
            ArrayList<Line3D> lines = cube.getLines();
            for (Line3D line : lines) {
                Line2D line2D = camera.transformTo2D(line);
                g2D.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!e.isControlDown()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ADD -> camera.changeZoom(1);//+ zoom in
                case KeyEvent.VK_SUBTRACT -> camera.changeZoom(-1);//- zoom out
                case KeyEvent.VK_NUMPAD2 -> camera.moveByVector(0, 0, 1);//2 z negative
                case KeyEvent.VK_NUMPAD4 -> camera.moveByVector(-1, 0, 0);//4 x negative
                case KeyEvent.VK_NUMPAD7 -> camera.moveByVector(0, 1, 0);//7 y positive
                case KeyEvent.VK_NUMPAD8 -> camera.moveByVector(0, 0, -1);//8 z positive
                case KeyEvent.VK_NUMPAD1 -> camera.moveByVector(0, -1, 0);//1 y negative
                case KeyEvent.VK_NUMPAD6 -> camera.moveByVector(1, 0, 0);//6 x positive
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_NUMPAD2 -> camera.rotate(1, 0, 0);//tilt back
                case KeyEvent.VK_NUMPAD6 -> camera.rotate(0, 1, 0);//spin right
                case KeyEvent.VK_NUMPAD9 -> camera.rotate(0, 0, 1);//turn right
                case KeyEvent.VK_NUMPAD8 -> camera.rotate(-1, 0, 0);//tilt forward
                case KeyEvent.VK_NUMPAD4 -> camera.rotate(0, -1, 0);//spin left
                case KeyEvent.VK_NUMPAD7 -> camera.rotate(0, 0, -1);//turn left
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
