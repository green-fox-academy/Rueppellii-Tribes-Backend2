package rueppellii.backend2.tribes.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.persistence.repository.RoleRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ApplicationUserRole findById(Long id) throws UserRoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> new UserRoleNotFoundException("User role found by this id: " + id));
    }

    public void saveRole(ApplicationUserRole applicationUserRole) {
        roleRepository.save(applicationUserRole);
    }
}
