package com.api.parkingcontrol.service.exceptions;

import java.io.Serial;

public class EntityBadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityBadRequestException(String msg){
        super(msg);
    }
}
