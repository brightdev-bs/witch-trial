package vanilla.witchtrial.global.exception;

import lombok.Getter;
import vanilla.witchtrial.global.common.ErrorCode;

@Getter
public class DuplicatedEntityException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicatedEntityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
