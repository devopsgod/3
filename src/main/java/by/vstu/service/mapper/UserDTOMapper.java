package by.vstu.service.mapper;

import by.vstu.dto.RoleDTO;
import by.vstu.dto.user.UserDTO;
import by.vstu.model.user.User;
import by.vstu.service.user.RoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper implements EntityToDTOMapper<User, UserDTO> {

    private final RoleService roleService;
    private final RoleDTOMapper roleMapper;
    private final ModelMapper mapper = new ModelMapper();

    public UserDTOMapper(RoleService roleService, RoleDTOMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;

        mapper.addMappings(new PropertyMap<UserDTO, User>() {
            protected void configure() {
                skip(destination.getId());
                skip().setRole(null);
                skip().setDeleted(false);
            }
        });
        mapper.addMappings(new PropertyMap<User, UserDTO>() {
            protected void configure() {
                skip().setRoleId(null);
                skip().setRole(null);
            }
        });
    }

    @Override
    public UserDTO toDTO(User entity, Object... args) {
        UserDTO doo = mapper.map(entity, UserDTO.class);
        RoleDTO role = roleMapper.toDTO(entity.getRole());
        doo.setRole(role);
        doo.setRoleId(role.getId());
        return doo;
    }

    @Override
    public User toEntity(UserDTO dto, Object... args) {
        User entity = mapper.map(dto, User.class);
        entity.setRole(roleService.get(dto.getRoleId()));
        return entity;
    }
}
