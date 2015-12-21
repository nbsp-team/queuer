package com.nbsp.queuer.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


/**
 * Фабрика type адаптеров, выполняющая парсинг общего тела
 * ответов от серверного API
 */
public class ResponseAdapterFactory implements TypeAdapterFactory {
    public static final String RESPONSE = "Response";
    public static final String STATUS = "Status";
    public static final String ERROR = "Error";
    public static final String OK = "Ok";
    public static final String CODE = "Code";


    public < T > TypeAdapter < T > create(Gson gson, final TypeToken < T > type) {
        final TypeAdapter < T > delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter < JsonElement > elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter < T > () {
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in ) throws IOException {
                JsonElement jsonElement = elementAdapter.read(in);

                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    String status = jsonObject.get(STATUS).getAsString();
                    int code = jsonObject.getAsJsonPrimitive(CODE).getAsInt();

                    if (status.equals(OK)) {
                        jsonElement = jsonObject.get(RESPONSE);
                    } else {
                        String errorMessage = jsonElement.getAsJsonObject().get(ERROR).getAsString();
                        throw new ApiError(code, errorMessage);
                    }
                }

                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}