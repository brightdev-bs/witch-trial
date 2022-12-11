package vanilla.witchtrial.service;

import vanilla.witchtrial.domain.dto.LoginDto;
import vanilla.witchtrial.domain.dto.SignupDto;
import vanilla.witchtrial.domain.dto.UserDto;

public interface AccountService {

    Long signup(SignupDto signupDto);

    UserDto login(LoginDto loginDto);
}
