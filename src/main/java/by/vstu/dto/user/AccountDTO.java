package by.vstu.dto.user;

import by.vstu.dto.RoleDTO;
import by.vstu.util.CommonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class AccountDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotEmpty
    @Email(regexp = CommonUtils.EMAIL_REGEX)
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID roleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RoleDTO role;

    @NotEmpty
    @Size(min = 4, max = 80)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
