package vanilla.witchtrial.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXISTS_HEADER(HttpStatus.BAD_REQUEST, "Can't find a header info"),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't find the post"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't find the user"),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "Can't use the email. It is already being used"),
    INVALID_TYPE(HttpStatus.BAD_REQUEST, "Type deosn't match"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Incorrect Password"),
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, "Invalid Params"),
    ;

    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
