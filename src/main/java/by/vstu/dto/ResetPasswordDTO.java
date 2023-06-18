package by.vstu.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ResetPasswordDTO {

    @NotEmpty
    private String token;

    @NotEmpty
    private String password;
}
