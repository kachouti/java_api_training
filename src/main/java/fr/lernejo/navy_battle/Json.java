package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Json {
    public final String ID;
    public final String URL;
    public final String MSG;

    public Json(
        @JsonProperty("id") JsonNode id,
        @JsonProperty("url") JsonNode url,
        @JsonProperty("message") JsonNode message) {

        this.ID = id.toString();
        this.URL = url.toString();
        this.MSG = message.toString();
    }

}
