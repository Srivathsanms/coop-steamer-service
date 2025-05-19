package no.coop.task.streamer.exceptions;

public class ApiClientRequestException extends RuntimeException {

    public ApiClientRequestException(String message) {
        super(message);
    }
}
