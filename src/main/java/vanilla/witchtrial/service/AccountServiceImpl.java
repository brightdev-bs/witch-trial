package vanilla.witchtrial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vanilla.witchtrial.domain.User;
import vanilla.witchtrial.dto.LoginDto;
import vanilla.witchtrial.dto.SignupDto;
import vanilla.witchtrial.dto.UserDto;
import vanilla.witchtrial.global.common.constants.ErrorCode;
import vanilla.witchtrial.global.exception.DuplicatedEntityException;
import vanilla.witchtrial.global.exception.InvalidPasswordException;
import vanilla.witchtrial.global.exception.NotFoundException;
import vanilla.witchtrial.repository.UserRepository;

import java.util.Optional;

import static vanilla.witchtrial.global.common.constants.ErrorCode.DUPLICATE_USER;
import static vanilla.witchtrial.global.common.constants.ErrorCode.INVALID_PASSWORD;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;

    public Long signup(SignupDto signupDto) {
        Optional<User> findUser = userRepository.findByEmail(signupDto.getEmail());
        if(findUser.isPresent()) {
            throw new DuplicatedEntityException(DUPLICATE_USER);
        }

        User newUser = User.of(signupDto.getEmail(), signupDto.getUsername(), signupDto.getPassword());
        userRepository.save(newUser);
        return newUser.getId();
    }

    @Override
    public UserDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        if (!user.getPassword().equals(loginDto.getPassword())) {
            new InvalidPasswordException(INVALID_PASSWORD);
        }

        return UserDto.of(
                        user.getEmail(),
                        user.getPassword(),
                        user.getUsername()
                );
    }
}
