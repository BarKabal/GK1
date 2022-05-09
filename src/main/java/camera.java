import org.ejml.simple.SimpleMatrix;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class camera {
    final static double basicMove = 0.1;
    final static double basicTurn = Math.sin(0.1);
    final static double basicZoom = 0.1;
    private double x, y, z;
    private double xTilt, yTilt, zTilt;
    private double zoom;
    private SimpleMatrix calculationMatrix;

    public camera() {
        x = 0;
        y = 0;
        z = 0;
        xTilt = 0;
        yTilt = 0;
        zTilt = 0;
        zoom = 2;
        calculateMatrix();
    }

    public void moveByVector(int xSign, int ySign, int zSign) {
        x += xSign*basicMove;
        y += ySign*basicMove;
        z += zSign*basicMove;
        calculateMatrix();
    }

    public void rotate(int xSign, int ySign, int zSign) {
        xTilt += xSign*basicTurn;
        yTilt += ySign*basicTurn;
        zTilt += zSign*basicTurn;
        calculateMatrix();
    }

    public void changeZoom(int sign) {
        zoom += sign*basicZoom;
        calculateMatrix();
    }

    public Line2D transformTo2D(Line3D line) {
        Point2D p1 = transformTo2D(line.point1);
        Point2D p2 = transformTo2D(line.point2);
        return new Line2D.Double(p1, p2);
    }

    public Point2D transformTo2D(Point3D point) {
        SimpleMatrix p1 = new SimpleMatrix(4, 1, true, new double[]{
                point.x, point.y, point.z, 1
        });
        SimpleMatrix p1Matrix = calculationMatrix.mult(p1);
        double d = p1Matrix.get(3, 0);
        Point3D newPoint1 = new Point3D(p1Matrix.get(0, 0) / d, p1Matrix.get(1, 0) / d, p1Matrix.get(2, 0) / d);
        int xl = (int) (newPoint1.x * gui.width / newPoint1.z + gui.width / 2);
        int yl = (int) (-newPoint1.y * gui.height / newPoint1.z + gui.height / 2);
        return new Point2D.Double(xl, yl);
    }

    public void calculateMatrix() {
        SimpleMatrix zoomMatrix = new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1 / (1 - zoom), (zoom) / (zoom - 1),
                0, 0, 1, 0
        });
        SimpleMatrix xRotMatrix = new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, 0,
                0, Math.cos(xTilt), -Math.sin(xTilt), 0,
                0, Math.sin(xTilt), Math.cos(xTilt), 0,
                0, 0, 0, 1
        });
        SimpleMatrix yRotMatrix = new SimpleMatrix(4, 4, true, new double[]{
                Math.cos(yTilt), 0, Math.sin(yTilt), 0,
                0, 1, 0, 0,
                -Math.sin(yTilt), 0, Math.cos(yTilt), 0,
                0, 0, 0, 1
        });
        SimpleMatrix zRotMatrix = new SimpleMatrix(4, 4, true, new double[]{
                Math.cos(zTilt), -Math.sin(zTilt), 0, 0,
                Math.sin(zTilt), Math.cos(zTilt), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
        SimpleMatrix moveMatrix = new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, -x,
                0, 1, 0, -y,
                0, 0, 1, -z,
                0, 0, 0, 1
        });
        calculationMatrix = zoomMatrix.mult(zRotMatrix.mult(yRotMatrix.mult(xRotMatrix.mult(moveMatrix))));
    }
}
