package school.sptech.crud_proj_v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntidadeNotFoundException extends RuntimeException {
    public EntidadeNotFoundException(String message) {
        super(message);
    }
}
