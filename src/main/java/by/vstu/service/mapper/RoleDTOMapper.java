package by.vstu.service.mapper;

import by.vstu.dto.RoleDTO;
import by.vstu.model.user.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleDTOMapper implements EntityToDTOMapper<Role, RoleDTO> {

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public RoleDTO toDTO(Role entity, Object... args) {
        return mapper.map(entity, RoleDTO.class);
    }

    @Override
    public Role toEntity(RoleDTO dto, Object... args) {
        return mapper.map(dto, Role.class);
    }
}
