package az.code.carlada.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ModelNotFound extends RuntimeException {
    public ModelNotFound(String message) {
        super(message);
    }
}