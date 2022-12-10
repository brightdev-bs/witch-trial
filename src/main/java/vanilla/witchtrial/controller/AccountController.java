package vanilla.witchtrial.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vanilla.witchtrial.domain.dto.LoginDto;
import vanilla.witchtrial.domain.dto.SignupDto;
import vanilla.witchtrial.global.response.ApiResponse;
import vanilla.witchtrial.service.AccountService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody @Valid SignupDto signupDto) {
        accountService.signup(signupDto);
        return ApiResponse.of(HttpStatus.CREATED.toString(), "성공");
    }
}
