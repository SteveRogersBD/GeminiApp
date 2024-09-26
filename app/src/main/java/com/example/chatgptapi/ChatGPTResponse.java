package com.example.chatgptapi;

import java.util.List;

public class ChatGPTResponse {
    private List<Choice> choices; // List of choices returned from the API

    // Constructor
    public ChatGPTResponse(List<Choice> choices) {
        this.choices = choices;
    }

    // Getter for choices
    public List<Choice> getChoices() {
        return choices;
    }

    // Inner class representing a choice
    public static class Choice {
        private Message message; // The message from the assistant

        // Constructor
        public Choice(Message message) {
            this.message = message;
        }

        // Getter for message
        public Message getMessage() {
            return message;
        }
    }

    // Inner class representing a message
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

