import com.fasterxml.jackson.annotation.JsonInclude;

public class Message {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recipientId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senderId;

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

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "recipientId='" + recipientId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
