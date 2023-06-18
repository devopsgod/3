package by.vstu.controller.admin;

import by.vstu.dto.RoleDTO;
import by.vstu.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/role")
public class AdminRoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleDTO> getRoles() {
        return roleService.getRoles();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return roleService.create(roleDTO);
    }

    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable UUID id, @Valid @RequestBody RoleDTO roleDTO) {
        return roleService.update(id, roleDTO);
    }


}
