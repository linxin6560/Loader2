package com.levylin.lib.loader.cache.exception;

import retrofit2.Response;

/**
 * Exception for an unexpected, non-2xx HTTP response.
 * Created by LinXin on 2016/12/2 15:15.
 */
public final class ResponseException extends Exception {
    private final int code;
    private final String message;
    private final transient Response<?> response;

    public ResponseException(Response<?> response) {
        super("HTTP " + response.code() + " " + response.message());
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    /**
     * HTTP status code.
     */
    public int code() {
        return code;
    }

    /**
     * HTTP status message.
     */
    public String message() {
        return message;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response<?> response() {
        return response;
    }
}