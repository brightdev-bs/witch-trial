package vanilla.witchtrial.dto;

import vanilla.witchtrial.domain.UserAccount;

public record UserDto(
        Long id,
        String email,
        String password,
        String username
) {

    public static UserDto of(String email, String password, String username) {
        return new UserDto(null, email, password, username);
    }

    public static UserDto of(Long id, String email, String password, String username) {
        return new UserDto(id, email, password, username);
    }

    public static UserDto from(UserAccount entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getUsername()
        );
    }
}
