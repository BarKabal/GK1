import java.util.ArrayList;

public class cube {
    Point3D p111,p112,p121,p122,p211,p212,p221,p222;
    Line3D l11x,l1x1,lx11,lx21,l2x1,l12x,lx22,l2x2,l22x,l21x,lx12,l1x2;
    ArrayList<Line3D> lines;

    public cube(double x1, double x2, double y1, double y2, double z1, double z2) {
        p111 = new Point3D(x1,y1,z1);
        p112 = new Point3D(x1,y1,z2);
        p121 = new Point3D(x1,y2,z1);
        p122 = new Point3D(x1,y2,z2);
        p211 = new Point3D(x2,y1,z1);
        p212 = new Point3D(x2,y1,z2);
        p221 = new Point3D(x2,y2,z1);
        p222 = new Point3D(x2,y2,z2);
        lines = new ArrayList<>();
        l11x = new Line3D(p111,p112); lines.add(l11x);
        l1x1 = new Line3D(p111,p121); lines.add(l1x1);
        lx11 = new Line3D(p111,p211); lines.add(lx11);
        lx21 = new Line3D(p121,p221); lines.add(lx21);
        l2x1 = new Line3D(p211,p221); lines.add(l2x1);
        l12x = new Line3D(p121,p122); lines.add(l12x);
        lx22 = new Line3D(p122,p222); lines.add(lx22);
        l2x2 = new Line3D(p212,p222); lines.add(l2x2);
        l22x = new Line3D(p221,p222); lines.add(l22x);
        l21x = new Line3D(p211,p212); lines.add(l21x);
        lx12 = new Line3D(p112,p212); lines.add(lx12);
        l1x2 = new Line3D(p112,p122); lines.add(l1x2);
    }

    public ArrayList<Line3D> getLines() {
        return lines;
    }
}
