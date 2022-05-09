import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class loader {

    private final ArrayList<cube> cubes;

    public loader() {
        cubes = new ArrayList<>();
    }

    public void load(String filename) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    if (line.equals("cube")) {
                        double x1, x2, y1, y2, z1, z2;
                        x1 = readParameter(reader);
                        x2 = readParameter(reader);
                        y1 = readParameter(reader);
                        y2 = readParameter(reader);
                        z1 = readParameter(reader);
                        z2 = readParameter(reader);
                        cubes.add(new cube(x1, x2, y1, y2, z1, z2));
                    }
                } else {
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double readParameter(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        return Double.parseDouble(line.substring(3));
    }

    public ArrayList<cube> getCubes() {
        return cubes;
    }
}
