package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class PostHandler implements HttpHandler {

    private final StringBuilder stringBuilder = new StringBuilder();
    PostHandler(int port) {
        this.stringBuilder.append("http://localhost:").append(port);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            error(exchange);
        }
        else {
            Json body = parser(exchange);
            if (body.url.equals("\"\"") || body.id.equals("\"\"") || body.message.equals("\"\"") || body == null) {
                msg(exchange, "format no valide", 400);
            }
            else {
                msg(exchange, "{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\"" + this.stringBuilder + "\",\n\t\"message\":\"May the best code win\"\n}", 202);
            }
            var game = new Jeu(body, body);
            try { game.initGame(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void msg(HttpExchange exchange, String message, int code) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    private void error(HttpExchange exchange) throws IOException {
        String body = "Not Found !";
        exchange.sendResponseHeaders(404, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    private String formatConversion(InputStream str) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        while ((i = str.read()) > 0) {
            stringBuilder.append((char) i);
        }
        return stringBuilder.toString();
    }

    private Json parser(HttpExchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Json body = null;
        String streamString = formatConversion(exchange.getRequestBody());
        if (streamString.isBlank()) {
            return null;
        }
        else {
            try {
                body = objectMapper.readValue(streamString, Json.class);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, "not exist".length());
                throw new IllegalArgumentException();
            }
        }
        return body;
    }
}
