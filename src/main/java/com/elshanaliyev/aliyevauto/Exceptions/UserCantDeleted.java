package com.elshanaliyev.aliyevauto.Exceptions;

public class UserCantDeleted extends RuntimeException{
    public UserCantDeleted(String message) {
        super(message);
    }
}
