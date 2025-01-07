import io.javalin.websocket.WsContext;

public class User {

    private String id;
    private final WsContext wsContext;

    public User(String id, WsContext wsContext) {
        this.id = id;
        this.wsContext = wsContext;
    }

    public void receiveMessage(Message message) {
        if (wsContext.session.isOpen()) {
            wsContext.send(message);
        }
    }

}
