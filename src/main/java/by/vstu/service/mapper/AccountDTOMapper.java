package by.vstu.service.mapper;

import by.vstu.dto.user.AccountDTO;
import by.vstu.model.user.User;
import by.vstu.service.user.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountDTOMapper implements EntityToDTOMapper<User, AccountDTO> {

    private final ModelMapper mapper = new ModelMapper();
    private final RoleService roleService;
    private final RoleDTOMapper roleMapper;

    public AccountDTOMapper(RoleService roleService, RoleDTOMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;

        mapper.addMappings(new PropertyMap<AccountDTO, User>() {
            protected void configure() {
                skip(destination.getId());
                skip().setRole(null);
            }
        });
        mapper.addMappings(new PropertyMap<User, AccountDTO>() {
            protected void configure() {
                skip().setRoleId(null);
                skip().setRole(null);
            }
        });
    }

    @Override
    public AccountDTO toDTO(User entity, Object... args) {
        AccountDTO doo = mapper.map(entity, AccountDTO.class);
        doo.setRole(roleMapper.toDTO(entity.getRole()));
        return doo;
    }

    @Override
    public User toEntity(AccountDTO dto, Object... args) {
        User entity = mapper.map(dto, User.class);
        entity.setRole(roleService.get(dto.getRoleId()));
        return entity;
    }
}
