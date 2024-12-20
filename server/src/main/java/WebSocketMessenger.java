import io.javalin.websocket.*;

import java.util.HashMap;
import java.util.Map;

public class WebSocketMessenger implements WebSocketConnection {

    private static final WebSocketMessenger messenger = new WebSocketMessenger();

    public static WebSocketMessenger get() {
        return messenger;
    }

    private final Map<String, User> connections;

    private WebSocketMessenger() {
        this.connections = new HashMap<>();
    }

    private String getShortContextId(WsContext context) {
        return context.sessionId().split("-")[0];
    }

    private Message getUsersInChatMessage() {
        String message = String.join(",", connections.keySet()) + " are in the chat.";
        return new Message(message);
    }

    private void sendAllUserMessage(Message message) {
        connections
                .values()
                .forEach(user -> user.receiveMessage(message));
    }

    private void sendUserMessage(Message message) {
        connections.get(message.getRecipientId()).receiveMessage(message);
    }

    private void replyUserMessage(Message message, WsContext context) {
        context.send(message);
    }

    @Override
    public void connect(WsConnectContext context) {
        System.out.println("Connected");
        String contextId = getShortContextId(context);
        System.out.println(contextId);
        connections.put(contextId, new User(context.sessionId(), context));
        System.out.println(connections);
        replyUserMessage(getUsersInChatMessage(), context);
        sendAllUserMessage(new Message(contextId + " has entered the chat."));
    }

    @Override
    public void message(WsMessageContext context) {
        System.out.println("Message");
        Message message = context.messageAsClass(Message.class);
        String recipientId = message.getRecipientId();
        String contextId = getShortContextId(context);


        if (recipientId.isEmpty()) {
            message.prependSenderId(contextId);
            sendAllUserMessage(message);
        } else if (connections.containsKey(recipientId)) {
            message.prependSenderId(contextId + "->" + recipientId);
            sendUserMessage(message);
            replyUserMessage(message, context);
        } else {
            replyUserMessage(new Message(recipientId + " is not in the chat."), context);
        }
    }

    @Override
    public void disconnect(WsCloseContext context) {
        System.out.println("Disconnect");
        String contextId = getShortContextId(context);
        connections.remove(contextId);
        sendAllUserMessage(new Message(contextId + " has left the chat."));
    }

    @Override
    public void error(WsErrorContext wsErrorContext) {
        System.out.println("Error");
    }
}
