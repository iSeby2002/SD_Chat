package demo.chat.controllers;

import demo.chat.dtos.ChatMessage;
import demo.chat.dtos.TypingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {
        "http://frontend.localhost",
        "http://user.localhost",
        "http://device.localhost",
        "http://monitoring.localhost",
        "http://chat.localhost",
        "http://localhost:3000",
        "http://localhost:8080",
        "http://localhost:8081",
        "http://localhost:8082",
        "http://localhost:8083",
        "https://heroic-boba-6ce237.netlify.app"})
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/private-message/client")
    public void sendPrivateMessageClient(@Payload ChatMessage message) {
        message.setStatus("DELIVERED");
        Map<String, ChatMessage> payload = new HashMap<>();
        payload.put("message", message);
        messagingTemplate.convertAndSend("/topic/alerts/client/" + message.getRecipientId(), payload);
    }

    @MessageMapping("/private-message/admin")
    public void sendPrivateMessageAdmin(@Payload ChatMessage message) {
        message.setStatus("DELIVERED");
        Map<String, ChatMessage> payload = new HashMap<>();
        payload.put("message", message);
        messagingTemplate.convertAndSend("/topic/alerts/admin/" + message.getRecipientId(), payload);
    }

    @MessageMapping("/message-read/admin")
    public void markMessageAsReadAdmin(@Payload ChatMessage message) {
        message.setStatus("READ");
        Map<String, ChatMessage> payload = new HashMap<>();
        payload.put("message", message);
        messagingTemplate.convertAndSend("/topic/alerts/client/" + message.getSenderId(), payload);
    }

    @MessageMapping("/message-read/client")
    public void markMessageAsReadClient(@Payload ChatMessage message) {
        message.setStatus("READ");
        Map<String, ChatMessage> payload = new HashMap<>();
        payload.put("message", message);
        messagingTemplate.convertAndSend("/topic/alerts/admin/" + message.getSenderId(), payload);
    }

    @MessageMapping("/typing-message/admin")
    public void typingMessageAdmin(@Payload TypingMessage typingMessage) {
        Map<String, TypingMessage> payload = new HashMap<>();
        payload.put("message", typingMessage);
        messagingTemplate.convertAndSend("/topic/alerts/client/typing/" + typingMessage.getRecipientId(), payload);
    }

    @MessageMapping("/typing-message/client")
    public void typingMessageClient(@Payload TypingMessage typingMessage) {
        Map<String, TypingMessage> payload = new HashMap<>();
        payload.put("message", typingMessage);
        messagingTemplate.convertAndSend("/topic/alerts/admin/typing/" + typingMessage.getRecipientId(), payload);
    }

}
