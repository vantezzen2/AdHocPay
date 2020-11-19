package io.vantezzen.adhocpay.models.user;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * We need a custom Gson Deserializer for the User object as we want to reuse existing User instances when possible
 */
public class UserDeserializer implements JsonDeserializer<User> {
    UserRepository repository;

    /**
     * Erzeuge einen Nutzer Deserializer
     *
     * @param repository Repository, welche für die Nutzerinstanzen genutzt werden soll
     */
    public UserDeserializer(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String username = obj.get("username").getAsString();

        return repository.getUserByName(username);
    }
}
