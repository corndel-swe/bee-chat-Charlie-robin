import com.fasterxml.jackson.annotation.JsonInclude;

public class Message {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recipientId;
    private String content;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public Message(String recipientId, String content) {
        this.recipientId = recipientId;
        this.content = content;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void prependSenderId(String senderId) {
        this.content = String.format("[%s] : %s", senderId, this.content);
    }

    @Override
    public String toString() {
        return "Message{" +
                "recipientId='" + recipientId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
