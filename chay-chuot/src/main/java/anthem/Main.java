package anthem;

import java.awt.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        Robot robot = new Robot();
        int prevx = -1;
        int prevy = -1;

        while (true) {
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            int x = (int) mouseLocation.getX();
            int y = (int) mouseLocation.getY();
            if (x == prevx && y == prevy) {
                int newx = x < 6 ? x + 5 : (x > 1910 ? x - 5 : (random.nextBoolean() ? x + 5 : x - 5));
                int newy = y < 6 ? y + 5 : (y > 1070 ? y - 5 : (random.nextBoolean() ? y + 5 : y - 5));
                robot.mouseMove(newx, newy);
                robot.delay(59_999);
            }
            prevx = x;
            prevy = y;
        }
    }
}
