import io.javalin.websocket.*;

public interface WebSocketConnection {
    void connect(WsConnectContext context);
    void message(WsMessageContext context);
    void disconnect(WsCloseContext context);
    void error(WsErrorContext wsErrorContext);
}
