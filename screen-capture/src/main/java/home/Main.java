package home;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        robot.setAutoDelay(99);

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
                        if ('a' <= c && c <= 'z') type(65 + c - 'a');
                        else if ('A' <= c && c<= 'Z') shiftType(65 + c - 'a');
                        else if ('0' <= c && c <= '9') type(48 + c - '0');
                        else if (c == '`') type(192);
                        else if (c == '~') shiftType(192);
                        else if (c == '!') shiftType(49);
                        else if (c == '@') shiftType(50);
                        else if (c == '#') shiftType(51);
                        else if (c == '$') shiftType(52);
                        else if (c == '%') shiftType(53);
                        else if (c == '^') shiftType(54);
                        else if (c == '&') shiftType(55);
                        else if (c == '*') shiftType(56);
                        else if (c == '(') shiftType(57);
                        else if (c == ')') shiftType(48);
                        else if (c == '-') type(173);
                        else if (c == '_') shiftType(173);
                        else if (c == '=') type(61);
                        else if (c == '+') shiftType(61);
                        else if (c == '[') type(219);
                        else if (c == '{') shiftType(219);
                        else if (c == ']') type(221);
                        else if (c == '}') shiftType(221);
                        else if (c == '\\') type(220);
                        else if (c == '|') shiftType(220);
                        else if (c == ';') type(59);
                        else if (c == ':') shiftType(59);
                        else if (c == '\'') type(222);
                        else if (c == '"') shiftType(222);
                        else if (c == ',') type(188);
                        else if (c == '<') shiftType(188);
                        else if (c == '.') type(190);
                        else if (c == '>') shiftType(190);
                        else if (c == '/') type(191);
                        else if (c == '?') shiftType(191);
                        else if (c == ' ') type(32);
                    }
                    if (action.action.equals("typeAndEnter")) {
                        System.out.println("typeAndEnter");
                        type(KeyEvent.VK_ENTER);
                    }
                    sendScreen();
                }
                else if (action.action.equals("ESC")) {
                    type(KeyEvent.VK_ESCAPE);
                    sendScreen();
                }
            }
        }
    }

    private static ResponseEntity<String> sendScreen() throws Exception {
        BufferedImage screenFullImage = robot.createScreenCapture(rectangle);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(900_000);
        ImageIO.write(screenFullImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        System.out.println(bytes.length);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);
        String url = String.format("http://%s:%s/screen-upload", host, port);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity , String.class);
    }

    private static void type(int keycode) {
        robot.keyPress(keycode);
        robot.keyRelease(keycode);
    }

    private static void shiftType(int keycode) {
        robot.keyPress(16);
        type(keycode);
        robot.keyRelease(16);
    }
}
