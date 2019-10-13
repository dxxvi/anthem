package home;

import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

public class Main {
    public static final Gson gson = new Gson();
    private static final HttpClient httpClient = new HttpClient();
    private static final WebSocketClient webSocketClient = new WebSocketClient();
    private static final WebSocket webSocket = new WebSocket();

    public static long sleepInterval = 60_000;
    private static long lastTimeScreenSent = 0;

    private static Robot robot;
    private static Rectangle rectangle;

    private static String host;
    private static String port;

    public static void main(String[] args) throws Exception {
        httpClient.start();
        webSocketClient.start();

        host = System.getProperty("host", "ec2-3-16-89-198.us-east-2.compute.amazonaws.com");
        port = System.getProperty("port", "8080");

        rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        robot = new Robot();
        robot.setAutoDelay(82);
        robot.setAutoWaitForIdle(true);

        webSocketClient.connect(webSocket, new URI(String.format("ws://%s:%s/ws/screen-capture", host, port)),
                new ClientUpgradeRequest());

        ActionExecutor actionExecutor = new ActionExecutor(robot);
        webSocket.setActionExecutor(actionExecutor);
        new Thread(actionExecutor).start();

        for (int i = 0; i < 5; i++)
            try {
                sendScreen();
                webSocket.justUploadImage();
                Thread.sleep(sleepInterval);
            }
            catch (Throwable __) { /* who cares */ }

        httpClient.stop();
        webSocketClient.stop();
        System.exit(0);
    }

    public static void sendScreen() throws Exception {
        long now = System.currentTimeMillis();
        if (now - lastTimeScreenSent < 999)
            return;

        BufferedImage screenFullImage = robot.createScreenCapture(rectangle);

        BufferedImage bi = new BufferedImage(screenFullImage.getWidth(), screenFullImage.getHeight(),
                BufferedImage.TYPE_USHORT_555_RGB);
        Graphics2D g2D = bi.createGraphics();
        g2D.drawImage(screenFullImage, 0, 0, null);
        g2D.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(900_000);
        ImageIO.write(bi, "png", baos);
        byte[] bytes = baos.toByteArray();

        lastTimeScreenSent = now;
        httpClient.newRequest(String.format("http://%s:%s/screen-upload", host, port))
                .method(HttpMethod.POST)
                .content(new BytesContentProvider("image/png", bytes))
                .send();
        System.out.println(bytes.length / 1000 + "K");
    }
}
