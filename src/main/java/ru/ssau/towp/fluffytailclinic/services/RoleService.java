package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Получить все роли
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Получить роль по ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Получить роль по имени
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    // Создать новую роль
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Обновить роль
    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(roleDetails.getName());

        return roleRepository.save(role);
    }

    // Удалить роль
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}