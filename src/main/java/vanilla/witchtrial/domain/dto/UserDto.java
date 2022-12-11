package vanilla.witchtrial.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private String email;
    private String username;

}
