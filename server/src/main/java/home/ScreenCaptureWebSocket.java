package home;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class ScreenCaptureWebSocket implements WebSocketListener {
    private static final Logger log = LoggerFactory.getLogger(ScreenCaptureWebSocket.class);

    private Session session;

    private BoWebSocket boWebSocket;

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {

    }

    @Override
    public void onWebSocketText(String message) {
        Action action = Server.gson.fromJson(message, Action.class);
        if ("image_uploaded".equals(action.action))
            boWebSocket.imageUploaded();
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        session = null;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        log.debug("good");
        if (this.session != null && this.session.isOpen())
            this.session.close();
        this.session = session;
    }

    @Override
    public void onWebSocketError(Throwable cause) {

    }

    public void setBoWebSocket(BoWebSocket boWebSocket) {
        this.boWebSocket = boWebSocket;
    }

    public void sendToScreenCapture(String message) {
        if (session != null && session.isOpen())
            try {
                session.getRemote().sendString(message);
            }
            catch (Throwable __) { /* who cares */ }
    }
}
