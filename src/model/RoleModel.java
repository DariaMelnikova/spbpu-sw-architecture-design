package model;

import model.datastructures.Occupation;
import model.datastructures.Performance;
import model.datastructures.Role;
import model.datastructures.User;
import model.repositories.RoleRepository;

import java.util.List;

public class RoleModel {
    private RoleRepository roleRepository = new RoleRepository();

    public void addRole(
            String description,
            Performance performance,
            User actor
    ) {
        if (description == null || description.trim().equals("")) throw new IllegalStateException("Некорректное описание роли");
        if (performance == null) throw new IllegalStateException("Некорректный спектакль");
        if (actor == null || actor.getOccupation() != Occupation.ACTOR) throw new IllegalStateException("Некорректный актёр");

        roleRepository.add(new Role(-1, description, performance.getId(), false, actor.getId()));
    }

    public void acceptRole(Role role) {
        if (role == null) throw new IllegalStateException("Некорректная роль");

        roleRepository.acceptRole(role.getId());
    }

    public void removeRole(Role role) {
        if (role == null) throw new IllegalStateException("Некорректная роль");

        roleRepository.remove(role);
    }

    public List<Role> getAllRolesOnPerformance(Performance performance) {
        if (performance == null) throw new IllegalStateException("Некорректный спектакль");

        return roleRepository.getAllForPerformance(performance.getId());
    }

    public List<Role> getAllRolesForActor(User actor) {
        if (actor == null) throw new IllegalStateException("Некорректный актёр");

        return roleRepository.getAllForActor(actor.getId());
    }

}
