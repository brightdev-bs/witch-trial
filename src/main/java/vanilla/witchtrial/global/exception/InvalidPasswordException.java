package vanilla.witchtrial.global.exception;

import vanilla.witchtrial.global.common.constants.ErrorCode;

public class InvalidPasswordException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
