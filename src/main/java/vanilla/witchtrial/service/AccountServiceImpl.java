package vanilla.witchtrial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vanilla.witchtrial.domain.User;
import vanilla.witchtrial.domain.dto.SignupDto;
import vanilla.witchtrial.global.exception.DuplicatedEntityException;
import vanilla.witchtrial.repository.UserRepository;

import java.util.Optional;

import static vanilla.witchtrial.global.common.ErrorCode.DUPLICATE_USER;

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
}
