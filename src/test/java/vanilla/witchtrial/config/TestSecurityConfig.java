package vanilla.witchtrial.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import vanilla.witchtrial.domain.User;
import vanilla.witchtrial.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean private UserRepository userRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(User.of(
                "vanille",
                "vanille",
                "pw"
        )));
    }
}
