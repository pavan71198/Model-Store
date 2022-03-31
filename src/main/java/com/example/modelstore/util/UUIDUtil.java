package com.example.modelstore.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class UUIDUtil {
    public UUID toUUID(String id) throws ResponseStatusException {
        try{
            return UUID.fromString(id);
        }
        catch (IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
    }
}
