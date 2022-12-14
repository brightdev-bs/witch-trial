package vanilla.witchtrial.global.exception;

import lombok.Getter;
import vanilla.witchtrial.global.common.constants.ErrorCode;

@Getter
public class NotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
