package com.onedayfirm.gorodabot;

public class ExitException extends RuntimeException {

    final int status;

    public ExitException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }
}
