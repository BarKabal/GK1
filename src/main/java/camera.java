import org.ejml.simple.SimpleMatrix;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class camera {
    final static double basicMove = 10;
    final static double basicTurn = Math.toRadians(2);
    final static double basicZoom = 10;
    private final SimpleMatrix calculationMatrix;
    int zoom = 500;

    public camera() {
        calculationMatrix = new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }

    public void moveByVector(int xSign, int ySign, int zSign, Point3D point) {
        calculationMatrix.setTo(new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, xSign * basicMove,
                0, 1, 0, ySign * basicMove,
                0, 0, 1, zSign * basicMove,
                0, 0, 0, 1
        }));
        calculateMatrix(point);
    }

    public void moveByZoom(int sign, Point3D point) {
        point.z += zoom * sign;
    }

    public void xRotate(int sign, Point3D point) {
        calculationMatrix.setTo(new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, 0,
                0, Math.cos(sign * basicTurn), -Math.sin(sign * basicTurn), 0,
                0, Math.sin(sign * basicTurn), Math.cos(sign * basicTurn), 0,
                0, 0, 0, 1
        }));
        calculateMatrix(point);
    }

    public void yRotate(int sign, Point3D point) {
        calculationMatrix.setTo(new SimpleMatrix(4, 4, true, new double[]{
                Math.cos(sign * basicTurn), 0, Math.sin(sign * basicTurn), 0,
                0, 1, 0, 0,
                -Math.sin(sign * basicTurn), 0, Math.cos(sign * basicTurn), 0,
                0, 0, 0, 1
        }));
        calculateMatrix(point);
    }

    public void zRotate(int sign, Point3D point) {
        calculationMatrix.setTo(new SimpleMatrix(4, 4, true, new double[]{
                Math.cos(sign * basicTurn), -Math.sin(sign * basicTurn), 0, 0,
                Math.sin(sign * basicTurn), Math.cos(sign * basicTurn), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        }));
        calculateMatrix(point);
    }

    public Line2D transformTo2D(Line3D line) {
        Point2D p1 = transformTo2D(line.point1);
        Point2D p2 = transformTo2D(line.point2);
        return new Line2D.Double(p1, p2);
    }

    public Point2D.Double transformTo2D(Point3D point) {
        return new Point2D.Double(
                (point.x * zoom) / (point.z + zoom) + 400,
                (point.y * zoom) / (point.z + zoom) + 300
        );
    }

    public void changeZoom(double zoomSign) {
        if (zoom > 20)
            zoom += zoomSign * basicZoom;
    }

    public void calculateMatrix(Point3D point) {
        SimpleMatrix p = new SimpleMatrix(4, 1, true, new double[]{
                point.x, point.y, point.z, 1
        });
        SimpleMatrix pT = calculationMatrix.mult(p);
        point.setPoint(pT.get(0) / pT.get(3), pT.get(1) / pT.get(3), pT.get(2) / pT.get(3));
    }

    public void zeroCalculationMatrix() {
        calculationMatrix.setTo(new SimpleMatrix(4, 4, true, new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        }));
    }
}
