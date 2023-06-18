package by.vstu.service.user;

import by.vstu.dto.RoleDTO;
import by.vstu.exception.BusinessEntityNotFoundException;
import by.vstu.exception.BusinessException;
import by.vstu.model.user.Role;
import by.vstu.repository.RoleRepository;
import by.vstu.service.mapper.RoleDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;
    private final RoleDTOMapper roleVOMapper;

    @Transactional(readOnly = true)
    public Role get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessEntityNotFoundException("Role not found"));
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> getRoles() {
        return roleVOMapper.toDTOs(repository.findAll());
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> getAvailableRoles() {
        return roleVOMapper.toDTOs(repository.findByFreeAccessTrue());
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BusinessEntityNotFoundException("Role not found"));
    }

    @Transactional
    public RoleDTO create(RoleDTO roleDTO) {
        if (repository.existsByNameIgnoreCase(roleDTO.getName())) {
            throw new BusinessException("Role name should be unique");
        }
        Role role = roleVOMapper.toEntity(roleDTO);
        return roleVOMapper.toDTO(repository.save(role));
    }

    @Transactional
    public RoleDTO update(UUID roleId, RoleDTO roleDTO) {
        Role role = get(roleId);
        if (repository.existsByNameAndIdNot(roleDTO.getName(), roleId)) {
            throw new BusinessException("Role name should be unique");
        }
        role.setName(roleDTO.getName());
        role.setDisplayName(roleDTO.getDisplayName());
        return roleVOMapper.toDTO(repository.save(role));
    }
}
