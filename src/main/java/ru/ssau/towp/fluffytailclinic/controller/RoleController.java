package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Получить все роли
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Получить роль по ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    // Получить роль по имени
    @GetMapping("/name/{name}")
    public Role getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    // Создать новую роль
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    // Обновить роль
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        return roleService.updateRole(id, roleDetails);
    }

    // Удалить роль
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}