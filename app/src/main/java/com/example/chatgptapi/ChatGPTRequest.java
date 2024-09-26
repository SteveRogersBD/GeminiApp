package com.example.chatgptapi;

import java.util.List;

public class ChatGPTRequest {
    private String model; // The model to use, e.g., "gpt-3.5-turbo"
    private List<Message> messages; // List of messages for the conversation

    // Constructor
    public ChatGPTRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    // Getters
    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    // Inner class representing each message
    public static class Message {
        private String role; // Role of the message sender ("user" or "assistant")
        private String content; // The content of the message

        // Constructor
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getters
        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }
}

