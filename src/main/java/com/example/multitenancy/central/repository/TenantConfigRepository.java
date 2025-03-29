package com.example.multitenancy.central.repository;

import com.example.multitenancy.central.entity.TenantConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantConfigRepository extends JpaRepository<TenantConfigEntity, String> {
}
