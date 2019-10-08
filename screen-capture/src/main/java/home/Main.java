package home;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    private static Robot robot;
    private static Rectangle rectangle;
    private static RestTemplate restTemplate;
    private static String host;
    private static String port;

    public static void main(String[] args) throws Exception {
        host = System.getProperty("host", "ec2-3-16-89-198.us-east-2.compute.amazonaws.com");
        port = System.getProperty("port", "8080");

        ObjectMapper objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException { /* deliberately do nothing */ }
        });

        rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        robot = new Robot();
        robot.setAutoDelay(82);
        robot.setAutoWaitForIdle(true);

        ResponseEntity<String> responseEntity = sendScreen();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<Action> actions = objectMapper.readValue(responseEntity.getBody(),
                    new TypeReference<List<Action>>() {});
            for (Action action : actions) {
                if ("leftclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    sendScreen();
                }
                else if ("rightclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    sendScreen();
                }
                else if ("doubleclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(200);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    sendScreen();
                }
                else if (action.action.startsWith("type") && action.text != null) {
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
                        else if (c == 'A') shiftType(KeyEvent.VK_A);
                        else if (c == 'B') shiftType(KeyEvent.VK_B);
                        else if (c == 'C') shiftType(KeyEvent.VK_C);
                        else if (c == 'D') shiftType(KeyEvent.VK_D);
                        else if (c == 'E') shiftType(KeyEvent.VK_E);
                        else if (c == 'F') shiftType(KeyEvent.VK_F);
                        else if (c == 'G') shiftType(KeyEvent.VK_G);
                        else if (c == 'H') shiftType(KeyEvent.VK_H);
                        else if (c == 'I') shiftType(KeyEvent.VK_I);
                        else if (c == 'J') shiftType(KeyEvent.VK_J);
                        else if (c == 'K') shiftType(KeyEvent.VK_K);
                        else if (c == 'L') shiftType(KeyEvent.VK_L);
                        else if (c == 'M') shiftType(KeyEvent.VK_M);
                        else if (c == 'N') shiftType(KeyEvent.VK_N);
                        else if (c == 'O') shiftType(KeyEvent.VK_O);
                        else if (c == 'P') shiftType(KeyEvent.VK_P);
                        else if (c == 'Q') shiftType(KeyEvent.VK_Q);
                        else if (c == 'R') shiftType(KeyEvent.VK_R);
                        else if (c == 'S') shiftType(KeyEvent.VK_S);
                        else if (c == 'T') shiftType(KeyEvent.VK_T);
                        else if (c == 'U') shiftType(KeyEvent.VK_U);
                        else if (c == 'V') shiftType(KeyEvent.VK_V);
                        else if (c == 'X') shiftType(KeyEvent.VK_X);
                        else if (c == 'W') shiftType(KeyEvent.VK_W);
                        else if (c == 'Y') shiftType(KeyEvent.VK_Y);
                        else if (c == 'Z') shiftType(KeyEvent.VK_Z);
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
                        else if (c == '~') shiftType(192);
                        else if (c == '!') type(KeyEvent.VK_EXCLAMATION_MARK);
                        else if (c == '@') type(KeyEvent.VK_AT);
                        else if (c == '#') type(KeyEvent.VK_NUMBER_SIGN);
                        else if (c == '$') type(KeyEvent.VK_DOLLAR);
                        else if (c == '%') shiftType(53);
                        else if (c == '^') type(KeyEvent.VK_CIRCUMFLEX);
                        else if (c == '&') type(KeyEvent.VK_AMPERSAND);
                        else if (c == '*') type(KeyEvent.VK_ASTERISK);
                        else if (c == '(') type(KeyEvent.VK_LEFT_PARENTHESIS);
                        else if (c == ')') type(KeyEvent.VK_RIGHT_PARENTHESIS);
                        else if (c == '-') type(KeyEvent.VK_MINUS);
                        else if (c == '_') type(KeyEvent.VK_UNDERSCORE);
                        else if (c == '=') type(KeyEvent.VK_EQUALS);
                        else if (c == '+') type(KeyEvent.VK_PLUS);
                        else if (c == '[') type(KeyEvent.VK_OPEN_BRACKET);
                        else if (c == '{') type(KeyEvent.VK_BRACELEFT);
                        else if (c == ']') type(KeyEvent.VK_CLOSE_BRACKET);
                        else if (c == '}') type(KeyEvent.VK_BRACERIGHT);
                        else if (c == '\\') type(KeyEvent.VK_BACK_SLASH);
                        else if (c == '|') shiftType(220);
                        else if (c == ';') type(KeyEvent.VK_SEMICOLON);
                        else if (c == ':') type(KeyEvent.VK_COLON);
                        else if (c == '\'') type(KeyEvent.VK_QUOTE);
                        else if (c == '"') type(KeyEvent.VK_QUOTEDBL);
                        else if (c == ',') type(KeyEvent.VK_COMMA);
                        else if (c == '<') type(KeyEvent.VK_LESS);
                        else if (c == '.') type(KeyEvent.VK_PERIOD);
                        else if (c == '>') type(KeyEvent.VK_GREATER);
                        else if (c == '/') type(KeyEvent.VK_SLASH);
                        else if (c == '?') shiftType(191);
                        else if (c == ' ') type(KeyEvent.VK_SPACE);
                    }
                    if (action.action.equals("typeAndEnter"))
                        type(KeyEvent.VK_ENTER);
                    sendScreen();
                }
                else if (action.action.equals("ESC")) {
                    type(KeyEvent.VK_ESCAPE);
                    sendScreen();
                }
                else if (action.action.equals("CapsLock"))
                    type(KeyEvent.VK_CAPS_LOCK);
                else if (action.action.equals("Backspace")) {
                    type(KeyEvent.VK_BACK_SPACE);
                    sendScreen();
                }
                else if (action.action.equals("CtrlZ")) {
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.delay(82);
                    type(KeyEvent.VK_Z);
                    robot.delay(82);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    sendScreen();
                }
            }
        }
    }

    private static ResponseEntity<String> sendScreen() throws Exception {
        BufferedImage screenFullImage = robot.createScreenCapture(rectangle);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(900_000);
        ImageIO.write(screenFullImage, "png", baos);
        byte[] bytes = pngTasticOptimize(baos.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);
        String url = String.format("http://%s:%s/screen-upload", host, port);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity , String.class);
    }

    private static void type(int keycode) {
        try {
            robot.keyPress(keycode);
            robot.delay(82);
            robot.keyRelease(keycode);
            robot.delay(82);
        }
        catch (Throwable __) { /* who cares */ }
    }

    private static void shiftType(int keycode) {
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

    private static byte[] pngTasticOptimize(byte[] bytes) {
        PngOptimizer optimizer = new PngOptimizer();
        PngImage image = new PngImage(new ByteArrayInputStream(bytes));
        try {
            long startTime = System.currentTimeMillis();
            PngImage optimizedImage = optimizer.optimize(image, true, 5);
            ByteArrayOutputStream optimizedBytes = new ByteArrayOutputStream();
            final long optimizedSize = optimizedImage.writeDataOutputStream(optimizedBytes).size();
            System.out.printf("%d %dms %d\n", bytes.length, System.currentTimeMillis() - startTime, optimizedSize);
            return optimizedSize > bytes.length ? bytes : optimizedBytes.toByteArray();
        }
        catch (IOException __) {
            return bytes;
        }
    }
}
