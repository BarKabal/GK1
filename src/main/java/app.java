import java.util.ArrayList;

public class app {
    public static void main(String[] args) {
        camera camera = new camera();
        loader loader = new loader();
        loader.load("src/main/java/inputData.txt");
        ArrayList<cube> cubes = loader.getCubes();
        new gui(camera, cubes);
    }
}
