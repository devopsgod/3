package by.vstu.controller;

import by.vstu.dto.RoleDTO;
import by.vstu.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleDTO> getAvailableRoles() {
        return roleService.getAvailableRoles();
    }
}
