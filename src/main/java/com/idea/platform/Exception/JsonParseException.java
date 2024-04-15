package com.idea.platform.Exception;

public class JsonParseException extends RuntimeException {
    public JsonParseException(String message) {
        super("Ошибка при парсинге JSON");
    }

}
