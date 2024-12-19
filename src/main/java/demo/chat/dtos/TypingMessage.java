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
public class TypingMessage {
    private UUID senderId;
    private UUID recipientId;
    private Boolean isTyping;

    @Override
    public String toString() {
        return "TypingMessage{" +
                "senderId=" + senderId +
                "recipientId=" + recipientId +
                "isTyping=" + isTyping +
                '}';
    }
}
