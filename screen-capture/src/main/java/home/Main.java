package home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        String host = System.getProperty("host", "ec2-3-16-89-198.us-east-2.compute.amazonaws.com");
        String port = System.getProperty("port", "8080");
        RestTemplate restTemplate = new RestTemplate();

        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = new Robot();
        BufferedImage screenFullImage = robot.createScreenCapture(rectangle);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(900_000);
        ImageIO.write(screenFullImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        System.out.println(bytes.length);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        HttpEntity<byte[]> httpEntity = new HttpEntity<>(bytes, headers);
        String url = String.format("http://%s:%s/screen-upload", host, port);
        restTemplate.exchange(url, HttpMethod.POST, httpEntity , String.class);
    }
}
