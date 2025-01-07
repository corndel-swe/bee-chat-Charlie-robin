import io.javalin.Javalin;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        WebSocketConnection socketHandler = WebSocketMessenger.get();

        Javalin app = Javalin.create(javalinConfig -> {
            // Modifying the WebSocketServletFactory to set the socket timeout to 120 seconds
            javalinConfig.jetty.modifyWebSocketServletFactory(jettyWebSocketServletFactory ->
                    jettyWebSocketServletFactory.setIdleTimeout(Duration.ofSeconds(120))
            );
        });

        app.ws("/", ws -> {
            ws.onConnect(socketHandler::connect);
            ws.onMessage(socketHandler::message);
            ws.onClose(socketHandler::disconnect);
            ws.onError(socketHandler::error);
        });

        app.start(5001);
    }

}