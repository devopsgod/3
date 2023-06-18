package by.vstu.dto;

import by.vstu.util.CommonUtils;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateUnconfirmedAccountDTO {

    @NotEmpty
    @Email(regexp = CommonUtils.EMAIL_REGEX)
    private String email;

    private String role;
}
