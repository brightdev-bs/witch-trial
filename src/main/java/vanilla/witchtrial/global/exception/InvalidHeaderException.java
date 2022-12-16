package vanilla.witchtrial.global.exception;

import lombok.Getter;
import vanilla.witchtrial.global.common.constants.ErrorCode;

@Getter
public class InvalidHeaderException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidHeaderException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
