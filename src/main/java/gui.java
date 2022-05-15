import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class gui extends JFrame implements KeyListener {
    final static int width = 1200;
    final static int height = 1000;
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
                if (line.point1.z > -camera.zoom && line.point2.z > -camera.zoom) {
                    g2D.drawLine((int) Math.floor(line2D.getX1()),
                            (int) Math.floor(line2D.getY1()),
                            (int) Math.floor(line2D.getX2()),
                            (int) Math.floor(line2D.getY2()));
                }
            }
        }
        camera.zeroCalculationMatrix();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!e.isControlDown()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ADD -> {
                    camera.changeZoom(-1);
                    repaint();
                }
                case KeyEvent.VK_SUBTRACT -> {
                    camera.changeZoom(1);
                    repaint();
                }
                case KeyEvent.VK_NUMPAD2 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByVector(0, 0, 1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD4 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByVector(1, 0, 0, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD7 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByVector(0, 1, 0, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD8 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByVector(0, 0, -1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD1 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByVector(0, -1, 0, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD6 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByVector(-1, 0, 0, point);
                        }
                    }
                    repaint();
                }
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_NUMPAD2 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByZoom(1, point);
                            camera.xRotate(1, point);
                            camera.moveByZoom(-1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD6 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByZoom(1, point);
                            camera.yRotate(-1, point);
                            camera.moveByZoom(-1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD9 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.zRotate(-1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD8 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByZoom(1, point);
                            camera.xRotate(-1, point);
                            camera.moveByZoom(-1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD4 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.moveByZoom(1, point);
                            camera.yRotate(1, point);
                            camera.moveByZoom(-1, point);
                        }
                    }
                    repaint();
                }
                case KeyEvent.VK_NUMPAD7 -> {
                    for (cube cube : cubes) {
                        for (Point3D point : cube.points) {
                            camera.zRotate(1, point);
                        }
                    }
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
