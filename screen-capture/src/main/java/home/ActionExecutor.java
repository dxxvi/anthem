package home;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ActionExecutor implements Runnable {
    private final Robot robot;
    private final BlockingQueue<Action> actionBlockingQueue = new LinkedBlockingDeque<>();

    public ActionExecutor(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void run() {
        while (true) {
            boolean needToSendAnotherScreen = false;
            try {
                Action action = actionBlockingQueue.take();
                if ("leftclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    needToSendAnotherScreen = true;
                }
                else if ("rightclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    needToSendAnotherScreen = true;
                }
                else if ("doubleclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(200);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    needToSendAnotherScreen = true;
                }
                else if ("rightclickshift".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.delay(82);
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay(82);
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    robot.delay(82);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay(82);
                    needToSendAnotherScreen = true;
                }
                else if (action.action != null && action.action.startsWith("type") && action.text != null) {
                    for (char c : action.text.toCharArray()) {
                        if (c == 'a') type(KeyEvent.VK_A);
                        else if (c == 'b') type(KeyEvent.VK_B);
                        else if (c == 'c') type(KeyEvent.VK_C);
                        else if (c == 'd') type(KeyEvent.VK_D);
                        else if (c == 'e') type(KeyEvent.VK_E);
                        else if (c == 'f') type(KeyEvent.VK_F);
                        else if (c == 'g') type(KeyEvent.VK_G);
                        else if (c == 'h') type(KeyEvent.VK_H);
                        else if (c == 'i') type(KeyEvent.VK_I);
                        else if (c == 'j') type(KeyEvent.VK_J);
                        else if (c == 'k') type(KeyEvent.VK_K);
                        else if (c == 'l') type(KeyEvent.VK_L);
                        else if (c == 'm') type(KeyEvent.VK_M);
                        else if (c == 'n') type(KeyEvent.VK_N);
                        else if (c == 'o') type(KeyEvent.VK_O);
                        else if (c == 'p') type(KeyEvent.VK_P);
                        else if (c == 'q') type(KeyEvent.VK_Q);
                        else if (c == 'r') type(KeyEvent.VK_R);
                        else if (c == 's') type(KeyEvent.VK_S);
                        else if (c == 't') type(KeyEvent.VK_T);
                        else if (c == 'u') type(KeyEvent.VK_U);
                        else if (c == 'v') type(KeyEvent.VK_V);
                        else if (c == 'x') type(KeyEvent.VK_X);
                        else if (c == 'w') type(KeyEvent.VK_W);
                        else if (c == 'y') type(KeyEvent.VK_Y);
                        else if (c == 'z') type(KeyEvent.VK_Z);
                        else if (c == 'A') capsLockType(KeyEvent.VK_A);
                        else if (c == 'B') capsLockType(KeyEvent.VK_B);
                        else if (c == 'C') capsLockType(KeyEvent.VK_C);
                        else if (c == 'D') capsLockType(KeyEvent.VK_D);
                        else if (c == 'E') capsLockType(KeyEvent.VK_E);
                        else if (c == 'F') capsLockType(KeyEvent.VK_F);
                        else if (c == 'G') capsLockType(KeyEvent.VK_G);
                        else if (c == 'H') capsLockType(KeyEvent.VK_H);
                        else if (c == 'I') capsLockType(KeyEvent.VK_I);
                        else if (c == 'J') capsLockType(KeyEvent.VK_J);
                        else if (c == 'K') capsLockType(KeyEvent.VK_K);
                        else if (c == 'L') capsLockType(KeyEvent.VK_L);
                        else if (c == 'M') capsLockType(KeyEvent.VK_M);
                        else if (c == 'N') capsLockType(KeyEvent.VK_N);
                        else if (c == 'O') capsLockType(KeyEvent.VK_O);
                        else if (c == 'P') capsLockType(KeyEvent.VK_P);
                        else if (c == 'Q') capsLockType(KeyEvent.VK_Q);
                        else if (c == 'R') capsLockType(KeyEvent.VK_R);
                        else if (c == 'S') capsLockType(KeyEvent.VK_S);
                        else if (c == 'T') capsLockType(KeyEvent.VK_T);
                        else if (c == 'U') capsLockType(KeyEvent.VK_U);
                        else if (c == 'V') capsLockType(KeyEvent.VK_V);
                        else if (c == 'X') capsLockType(KeyEvent.VK_X);
                        else if (c == 'W') capsLockType(KeyEvent.VK_W);
                        else if (c == 'Y') capsLockType(KeyEvent.VK_Y);
                        else if (c == 'Z') capsLockType(KeyEvent.VK_Z);
                        else if (c == '0') type(KeyEvent.VK_0);
                        else if (c == '1') type(KeyEvent.VK_1);
                        else if (c == '2') type(KeyEvent.VK_2);
                        else if (c == '3') type(KeyEvent.VK_3);
                        else if (c == '4') type(KeyEvent.VK_4);
                        else if (c == '5') type(KeyEvent.VK_5);
                        else if (c == '6') type(KeyEvent.VK_6);
                        else if (c == '7') type(KeyEvent.VK_7);
                        else if (c == '8') type(KeyEvent.VK_8);
                        else if (c == '9') type(KeyEvent.VK_9);
                        else if (c == '`') type(KeyEvent.VK_BACK_QUOTE);
                        else if (c == '~') shiftType(KeyEvent.VK_BACK_QUOTE);
                        else if (c == '!') shiftType(KeyEvent.VK_1);
                        else if (c == '@') shiftType(KeyEvent.VK_2);
                        else if (c == '#') shiftType(KeyEvent.VK_3);
                        else if (c == '$') shiftType(KeyEvent.VK_4);
                        else if (c == '%') shiftType(KeyEvent.VK_5);
                        else if (c == '^') shiftType(KeyEvent.VK_6);
                        else if (c == '&') shiftType(KeyEvent.VK_7);
                        else if (c == '*') shiftType(KeyEvent.VK_8);
                        else if (c == '(') shiftType(KeyEvent.VK_9);
                        else if (c == ')') shiftType(KeyEvent.VK_0);
                        else if (c == '-') type(KeyEvent.VK_MINUS);
                        else if (c == '_') shiftType(KeyEvent.VK_MINUS);
                        else if (c == '=') type(KeyEvent.VK_EQUALS);
                        else if (c == '+') shiftType(KeyEvent.VK_EQUALS);
                        else if (c == '[') type(KeyEvent.VK_OPEN_BRACKET);
                        else if (c == '{') shiftType(KeyEvent.VK_OPEN_BRACKET);
                        else if (c == ']') type(KeyEvent.VK_CLOSE_BRACKET);
                        else if (c == '}') shiftType(KeyEvent.VK_CLOSE_BRACKET);
                        else if (c == '\\') type(KeyEvent.VK_BACK_SLASH);
                        else if (c == '|') shiftType(KeyEvent.VK_BACK_SLASH);
                        else if (c == ';') type(KeyEvent.VK_SEMICOLON);
                        else if (c == ':') shiftType(KeyEvent.VK_SEMICOLON);
                        else if (c == '\'') type(KeyEvent.VK_QUOTE);
                        else if (c == '"') shiftType(KeyEvent.VK_QUOTE);
                        else if (c == ',') type(KeyEvent.VK_COMMA);
                        else if (c == '<') shiftType(KeyEvent.VK_COMMA);
                        else if (c == '.') type(KeyEvent.VK_PERIOD);
                        else if (c == '>') shiftType(KeyEvent.VK_PERIOD);
                        else if (c == '/') type(KeyEvent.VK_SLASH);
                        else if (c == '?') shiftType(KeyEvent.VK_SLASH);
                        else if (c == ' ') type(KeyEvent.VK_SPACE);
                    }
                    if ("typeAndEnter".equals(action.action))
                        type(KeyEvent.VK_ENTER);
                    needToSendAnotherScreen = true;
                }
                else if ("ESC".equals(action.action)) {
                    type(KeyEvent.VK_ESCAPE);
                    needToSendAnotherScreen = true;
                }
                else if ("CapsLock".equals(action.action))
                    type(KeyEvent.VK_CAPS_LOCK);
                else if ("Backspace".equals(action.action)) {
                    type(KeyEvent.VK_BACK_SPACE);
                    needToSendAnotherScreen = true;
                }
                else if ("CtrlZ".equals(action.action)) {
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.delay(82);
                    type(KeyEvent.VK_Z);
                    robot.delay(82);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    needToSendAnotherScreen = true;
                }
                else if ("Sleep".equals(action.action) && action.x > 0 && Main.sleepInterval != action.x) {
                    Main.sleepInterval = action.x;
                    System.out.println("sleepInterval " + Main.sleepInterval + "ms");
                }
                else if ("Kill".equals(action.action))
                    System.exit(0);
                else if ("up".equals(action.action)) {
                    type(KeyEvent.VK_UP);
                    System.out.println("Up");
                    needToSendAnotherScreen = true;
                }
                else if ("down".equals(action.action)) {
                    type(KeyEvent.VK_DOWN);
                    System.out.println("Down");
                    needToSendAnotherScreen = true;
                }
                else if ("left".equals(action.action)) {
                    type(KeyEvent.VK_LEFT);
                    System.out.println("Left");
                    needToSendAnotherScreen = true;
                }
                else if ("right".equals(action.action)) {
                    type(KeyEvent.VK_RIGHT);
                    System.out.println("Right");
                    needToSendAnotherScreen = true;
                }

                if (needToSendAnotherScreen)
                    Main.sendScreen();
            }
            catch (Throwable __) { /* who cares */ }
        }
    }

    public void execute(Action action) {
        try {
            actionBlockingQueue.put(action);
        }
        catch (Throwable __) { /* who cares */ }
    }

    private void type(int keycode) {
        try {
            robot.keyPress(keycode);
            robot.delay(82);
            robot.keyRelease(keycode);
            robot.delay(194);
        }
        catch (Throwable __) { /* who cares */ }
    }

    private void capsLockType(int keycode) {
        try {
            robot.keyPress(KeyEvent.VK_CAPS_LOCK);
            robot.delay(82);
            robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
            robot.delay(82);
            type(keycode);
            robot.keyPress(KeyEvent.VK_CAPS_LOCK);
            robot.delay(82);
            robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
            robot.delay(82);
        }
        catch (Throwable __) { /* who cares */ }
    }

    private void shiftType(int keycode) {
        try {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.delay(82);
            type(keycode);
            robot.delay(82);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.delay(82);
        }
        catch (Throwable __) { /* who cares */ }
    }
}
