package vanilla.witchtrial.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class SignupDto implements Serializable {

    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotEmpty(message = "사용자 이름 입력은 필수 입니다.")
    private String username;

    @NotEmpty(message = "비밀번호 입력은 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 5자 이상이여야 합니다.")
    private String password;
}
