# AI Customer Service Chatbot

A real-time AI-powered customer service chatbot built with Spring Boot, WebSocket, and Llama 3.2 via Ollama. This chatbot can be customized for any small business by providing business-specific information, enabling 24/7 automated customer support.

## Project Overview

This chatbot serves as a virtual customer service representative that:
- Answers customer questions in real-time using WebSocket
- Provides business-specific responses based on configured information
- Uses local AI (Llama 3.2) via Ollama - no API costs
- Persists business information across restarts

## Tech Stack

- **Backend**: Spring Boot 3.2.3, Java 21
- **AI/ML**: LangChain4j, Ollama, Llama 3.2
- **Real-time Communication**: WebSocket (STOMP protocol)
- **Storage**: JSON file-based persistence
- **Build Tool**: Maven

## Prerequisites

- Java 21+
- Maven 3.9+
- Ollama installed locally
- Llama 3.2 model downloaded

## Getting Started

### 1. Install Ollama

Download and install Ollama from [https://ollama.com](https://ollama.com)

### 2. Download Llama 3.2 Model
```bash
ollama pull llama3.2
```

### 3. Clone and Build
```bash
git clone <your-repo-url>
cd chatbot
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## How to Use

### Step 1: Configure Business Information

Send a POST request to set up your business info:
```bash
POST http://localhost:8080/api/business/setup
Content-Type: application/json

{
  "businessName": "Zappie Webb",
  "businessType": "Online Clothing Store",
  "operatingHours": "24/7 Online",
  "location": "Online - Ships Worldwide",
  "returnPolicy": "30-day return policy",
  "deliveryInfo": "Free shipping on orders over $50",
  "contactInfo": "Instagram: @zappiewebb",
  "additionalInfo": "Trendy streetwear and casual clothing"
}
```

This information is saved to `business-info.json` and persists across restarts.

### Step 2: Open the Chat Interface

Navigate to: `http://localhost:8080/chat.html`

### Step 3: Start Chatting!

Ask questions like:
- "What are your hours?"
- "What's your return policy?"
- "Do you offer free shipping?"

## Architecture
```
Browser (WebSocket) → Spring Boot → ChatService → Ollama (port 11434) → Llama 3.2
                          ↓
                   Business Context
                   (business-info.json)
```

### Key Components

1. **WebSocketConfig**: Configures WebSocket endpoints and message brokers
2. **ChatService**: Manages AI model connection and business context injection
3. **BusinessStorageService**: Handles persistence of business information
4. **WebSocketChatController**: Routes real-time chat messages

## Project Structure
```
chatbot/
├── src/main/java/com/chitthi/chatbot/
│   ├── config/
│   │   └── WebSocketConfig.java
│   ├── controller/
│   │   ├── ChatController.java
│   │   └── WebSocketChatController.java
│   ├── model/
│   │   └── BusinessInfo.java
│   ├── service/
│   │   ├── ChatService.java
│   │   └── BusinessStorageService.java
│   └── ChatbotApplication.java
├── src/main/resources/
│   └── static/
│       └── chat.html
├── business-info.json (auto-generated)
├── pom.xml
└── README.md
```

## Configuration

### Application runs on port 8080 (Spring Boot)
### Ollama runs on port 11434

To change ports, update:
- `application.properties` for Spring Boot
- `ChatService.java` baseUrl for Ollama

## Features

- Real-time WebSocket communication
- Local AI processing (no API costs)
- Business context-aware responses
- Persistent business configuration
- Simple web-based chat interface
- Responsive design
- Connection status indicator

## Future Enhancements

- [ ] Multi-business support with unique IDs
- [ ] Database integration (PostgreSQL/MongoDB)
- [ ] Advanced analytics and logging
- [ ] Admin dashboard for business info management
- [ ] Conversation history
- [ ] Sentiment analysis
- [ ] Multi-language support

<div class="sp-embed-player" data-id="cOe6ernZUsY" data-aspect-ratio="1.777778" data-padding-top="56.250000%" style="position:relative;width:100%;padding-top:56.250000%;height:0;"><script src="https://go.screenpal.com/player/appearance/cOe6ernZUsY"></script><iframe style="position:absolute;top:0;left:0;width:100%;height:100%;border:0;"  scrolling="no" src="https://go.screenpal.com/player/cOe6ernZUsY?ff=1&ahc=1&dcc=1&tl=1&bg=transparent&share=1&download=1&embed=1&cl=1" allowfullscreen="true"></iframe></div>
