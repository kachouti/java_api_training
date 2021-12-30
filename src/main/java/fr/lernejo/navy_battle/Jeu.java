package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Jeu {
    public final Json Adversary;
    public final Json json;



    public void initGame() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(Adversary.url.substring(1, Adversary.url.length() - 1) + "/api/game/fire?cell=A1"))
            .setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET().build();
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());
    }
    Jeu(Json body1, Json body2) {
        this.Adversary = body1;
        this.json = body2;
    }
}
