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
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String host = System.getProperty("host", "ec2-3-16-89-198.us-east-2.compute.amazonaws.com");
        String port = System.getProperty("port", "8080");

        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException { /* deliberately do nothing */ }
        });

        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = new Robot();
        robot.setAutoDelay(99);
        BufferedImage screenFullImage = robot.createScreenCapture(rectangle);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(900_000);
        ImageIO.write(screenFullImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        System.out.println(bytes.length);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);
        String url = String.format("http://%s:%s/screen-upload", host, port);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity , String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<Action> actions = objectMapper.readValue(responseEntity.getBody(),
                    new TypeReference<List<Action>>() {});
            for (Action action : actions) {
                if ("leftclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    System.out.printf("leftclick %d %d\n", action.x, action.y);
                }
                else if ("rightclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    System.out.printf("rightclick %d %d\n", action.x, action.y);
                }
                else if ("doubleclick".equals(action.action) && action.x > 0 && action.y > 0) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(200);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    System.out.printf("doubleclick %d %d\n", action.x, action.y);
                }
                else if ("type".equals(action.action) && action.x > 0 && action.y > 0 && action.text != null) {
                    robot.mouseMove(action.x, action.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    // TODO robot.keyPress(...) and robot.keyRelease(...)
                }
            }
        }
    }
}
