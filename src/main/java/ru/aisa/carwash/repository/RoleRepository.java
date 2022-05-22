package ru.aisa.carwash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aisa.carwash.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
