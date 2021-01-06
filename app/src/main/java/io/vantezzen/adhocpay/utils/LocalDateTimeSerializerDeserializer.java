package io.vantezzen.adhocpay.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Custom GSON Serializer for LocalDateTime Objects
 * <p>
 * Source: https://www.javaguides.net/2019/11/gson-localdatetime-localdate.html
 */
public class LocalDateTimeSerializerDeserializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private static final String FORMAT = "u-M-d H:m:s";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        try {
            return LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern(FORMAT).withLocale(Locale.ENGLISH));
        } catch(DateTimeParseException e) {
            return LocalDateTime.now();
        }

    }
}
