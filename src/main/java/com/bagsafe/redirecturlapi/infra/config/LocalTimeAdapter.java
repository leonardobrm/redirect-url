package com.bagsafe.redirecturlapi.infra.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalTime.parse(json.getAsString(), formatter);
    }

    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(localTime.format(formatter));
    }
}
