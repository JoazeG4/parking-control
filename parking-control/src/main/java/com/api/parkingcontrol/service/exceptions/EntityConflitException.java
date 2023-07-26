package com.api.parkingcontrol.service.exceptions;

import java.io.Serial;

public class EntityConflitException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityConflitException(String msg){
        super(msg);
    }
}
