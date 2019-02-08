package rueppellii.backend2.tribes.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rueppellii.backend2.tribes.user.exceptions.UserRoleNotFoundException;
import rueppellii.backend2.tribes.user.persistence.dao.ApplicationUserRoleRepository;
import rueppellii.backend2.tribes.user.persistence.model.ApplicationUserRole;

@Service
public class RoleService {

    private ApplicationUserRoleRepository applicationUserRoleRepository;

    @Autowired
    public RoleService(ApplicationUserRoleRepository applicationUserRoleRepository) {
        this.applicationUserRoleRepository = applicationUserRoleRepository;
    }

    public ApplicationUserRole findById(Long id) throws UserRoleNotFoundException {
        return applicationUserRoleRepository.findById(id).orElseThrow(() -> new UserRoleNotFoundException("User role found by this id: " + id));
    }
}
