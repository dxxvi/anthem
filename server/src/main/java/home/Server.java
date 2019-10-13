package home;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import spark.Spark;

public class Server {
    public static final Gson gson = new Gson();

    public static long sleepInterval = 60_000;
    private static byte[] bytes = new byte[0];

    public static void main(String[] args) {
        BoWebSocket boWebSocket = new BoWebSocket();
        MeWebSocket meWebSocket = new MeWebSocket();
        ScreenCaptureWebSocket screenCaptureWebSocket = new ScreenCaptureWebSocket();

        boWebSocket.setScreenCaptureWebSocket(screenCaptureWebSocket);
        boWebSocket.setMeWebSocket(meWebSocket);
        screenCaptureWebSocket.setBoWebSocket(boWebSocket);

        Spark.port(8080);

        Spark.staticFileLocation("/static");

        Spark.webSocket("/ws/bo", boWebSocket);
        Spark.webSocket("/ws/me", meWebSocket);
        Spark.webSocket("/ws/screen-capture", screenCaptureWebSocket);

        Spark.post("/screen-upload", (request, response) -> {
            bytes = request.bodyAsBytes();
            response.status(HttpStatus.CREATED_201);
            return "";
        });

        Spark.get("/latest-screen", (request, response) -> {
            response.type("image/png");
            response.status(HttpStatus.OK_200);
            response.raw().getOutputStream().write(bytes);
            return null;
        });
    }
}
