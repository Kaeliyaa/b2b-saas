package com.bs.b2bsaas.repository;

import com.bs.b2bsaas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.email = :email AND u.tenant.id = :tenantId")
  Optional<User> findByEmailAndTenantId(String email, UUID tenantId);

  boolean existsByEmailAndTenantId(String email, UUID tenantId);
}
