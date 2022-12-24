package vanilla.witchtrial.global.common.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_EXISTS_HEADER(HttpStatus.BAD_REQUEST, "Header is necessary"),


    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "Can't use the email. It is already being used"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't find the user"),

    /*  POST Error */
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't find the post"),
    NOT_EXISTS_POST_TYPE(HttpStatus.BAD_REQUEST, "Post-type is necessary"),
    INVALID_POST_TYPE(HttpStatus.BAD_REQUEST, "Type deosn't match"),


    /* Common Error */
    NOT_FOUND_ENTITY(HttpStatus.BAD_REQUEST, "Can't find the entity"),
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, "Invalid Params"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Incorrect Password"),
    SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "Size limit exceeded"),

    /* Business Error */
    ILLEGAL_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, "Illegal client request")
    ;

    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
