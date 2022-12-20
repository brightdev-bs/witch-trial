package vanilla.witchtrial.service;

import vanilla.witchtrial.dto.LoginDto;
import vanilla.witchtrial.dto.SignupDto;
import vanilla.witchtrial.dto.UserDto;

public interface AccountService {

    Long signup(SignupDto signupDto);

    UserDto login(LoginDto loginDto);
}
