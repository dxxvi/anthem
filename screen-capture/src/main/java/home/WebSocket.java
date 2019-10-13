package home;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import java.nio.ByteBuffer;

public class WebSocket implements WebSocketListener {
    private Session session;
    private ActionExecutor actionExecutor;

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {

    }

    @Override
    public void onWebSocketText(String message) {
        Action action = Main.gson.fromJson(message, Action.class);
        actionExecutor.execute(action);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        session = null;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
    }

    @Override
    public void onWebSocketError(Throwable cause) {

    }

    public void setActionExecutor(ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
    }

    public void justUploadImage() {
        if (session != null && session.isOpen()) {
            Action action = new Action();
            action.action = "image_uploaded";
            try {
                session.getRemote().sendBytes(ByteBuffer.wrap(Main.gson.toJson(action).getBytes()));
            }
            catch (Throwable __) { /* who cares */ }
        }
    }
}
