package vanilla.witchtrial.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vanilla.witchtrial.domain.dto.LoginDto;
import vanilla.witchtrial.domain.dto.SignupDto;
import vanilla.witchtrial.domain.dto.UserDto;
import vanilla.witchtrial.global.response.ApiResponse;
import vanilla.witchtrial.service.AccountService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Valid LoginDto loginDto) {
        UserDto user = accountService.login(loginDto);
        return ApiResponse.of(HttpStatus.OK.toString(), user);
    }

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody @Valid SignupDto signupDto) {
        accountService.signup(signupDto);
        return ApiResponse.of(HttpStatus.CREATED.toString(), "회원가입되었습니다.");
    }
}
