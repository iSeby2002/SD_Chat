package demo.chat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private UUID senderId;
    private UUID recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private String status;
    private long timestamp;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", senderName='" + senderName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
