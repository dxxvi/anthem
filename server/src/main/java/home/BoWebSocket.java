package home;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoWebSocket implements WebSocketListener {
    private static final Logger log = LoggerFactory.getLogger(BoWebSocket.class);
    private static final Set<String> actionsForScreenCapture = new HashSet<>(Arrays.asList(
            "type", "typeAndEnter", "ESC", "Backspace", "CtrlZ", "Sleep", "leftclick", "rightclick", "doubleclick"
    ));

    private Session session;
    private MeWebSocket meWebSocket;
    private ScreenCaptureWebSocket screenCaptureWebSocket;

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {

    }

    @Override
    public void onWebSocketText(String message) {
        Action action = Server.gson.fromJson(message, Action.class);
        if (actionsForScreenCapture.contains(action.action))
            screenCaptureWebSocket.sendToScreenCapture(message);
        else if ("bo_sent".equals(action.action))
            meWebSocket.sendToMeHtml(message);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        session = null;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        log.debug("Got a connection from browser");
        if (this.session != null && this.session.isOpen())
            this.session.close();
        this.session = session;
    }

    @Override
    public void onWebSocketError(Throwable cause) {

    }

    public void setScreenCaptureWebSocket(ScreenCaptureWebSocket screenCaptureWebSocket) {
        this.screenCaptureWebSocket = screenCaptureWebSocket;
    }

    public void setMeWebSocket(MeWebSocket meWebSocket) {
        this.meWebSocket = meWebSocket;
    }

    public void imageUploaded() {
        if (session != null && session.isOpen())
            try {
                session.getRemote().sendString("{\"action\":\"image_uploaded\"}");
            }
            catch (Throwable __) { /* who cares */ }
    }
}
