package by.vstu.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends AccountDTO {

    private String tabel;

    @NotNull
    private Boolean locked;

    @NotNull
    private Boolean activated;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean deleted;
}
