package vanilla.witchtrial.service;

import vanilla.witchtrial.domain.dto.SignupDto;

public interface AccountService {

    Long signup(SignupDto signupDto);
}
