import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        WebSocketConnection socketHandler = WebSocketMessenger.get();
        Javalin app = Javalin.create();

        app.ws("/websocket", ws -> {
            ws.onConnect(socketHandler::connect);
            ws.onMessage(socketHandler::message);
            ws.onClose(socketHandler::disconnect);
            ws.onError(socketHandler::error);
        });

        app.start(5001);
    }

}