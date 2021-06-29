package az.code.carlada.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IllegalSignException extends RuntimeException{
    public IllegalSignException(String message) {
            super(message);
        }
}
