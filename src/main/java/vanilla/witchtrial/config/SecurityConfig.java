package vanilla.witchtrial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vanilla.witchtrial.dto.UserDto;
import vanilla.witchtrial.dto.UserPrincipal;
import vanilla.witchtrial.global.common.constants.ErrorCode;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.UserRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        return http.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll()).formLogin().and().build();
//        return http
//                .authorizeHttpRequests(auth -> auth
//                        // static 폴더 모두 허용
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        // 인증 필요 없는 페이지 추가
//                        .requestMatchers(
//                                HttpMethod.GET
//                        ).permitAll()
//                        // 그 외 모든 페이지는 인증이 필요함.
//                        .anyRequest().authenticated()
//                )
//                .formLogin().and()
//                .logout()
//                    .logoutSuccessUrl("/")
//                    .and()
//                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findByEmail(username)
                .map(UserDto::from)
                .map(UserPrincipal::from)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
