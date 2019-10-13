package home;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

public class MeWebSocket implements WebSocketListener {
    private Session session;

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {

    }

    @Override
    public void onWebSocketText(String message) {

    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        session = null;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        if (this.session != null && this.session.isOpen())
            this.session.close();
        this.session = session;
    }

    @Override
    public void onWebSocketError(Throwable cause) {

    }

    public void sendToMeHtml(String message) {
        // TODO
    }
}
